package org.example.client.core;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.exception.CommandIOException;
import org.example.exception.InvalidArgsException;
import org.example.managers.*;
import org.example.command.*;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;
import org.example.parser.*;
import org.example.utils.LocalDateTimeSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console implements Serializable {
    private Scanner scanner;
    private static boolean active;
    private CommandManager commandmanager;
    private LinkedList<String> commandsStack;
    private int stacksize;
    private static UDPSender sender;
    private static UDPReader reader;
    private IOProvider provider;

    public Console(CommandManager commandmanager, UDPSender sender, UDPReader reader, IOProvider provider)
    {
        this.scanner = new Scanner(System.in);
        this.active = true;
        this.commandmanager = commandmanager;
        this.provider=provider;
        this.commandsStack = new LinkedList<String>();
        this.stacksize = 0;
        this.sender = sender;
        this.reader = reader;
    }

    public IOProvider getProvider() {
        return provider;
    }

    public boolean isActive() {
        return active;
    }

    public Scanner getScanner() {
        return scanner;
    }

    //установка соединения с сервером, создание сендера и ридера и запуск цикла взаимодействий с сервером.
    public void start(UDPConnector connector) {
        boolean con = false;
        String host = "";
        int port = 0;
        Pattern pattern = Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$");
        connector.Connect("localhost",9999);
        sender = new UDPSender(connector.getDatagramSocket(), connector.getServerAddress(),connector.getServerPort());
        reader =  new UDPReader(connector.getDatagramSocket());
        int[] parametersptr = {-1};
        CommandShallow shallow = new CommandShallow();
        while (this.active) {
            readCommand(provider);
        }
    }

    public void print(String line) {
        if (line.equals(null)) {
            return;
        }
        System.out.print(line);
    }

    public void println(String line) {
        if (line.equals(null)){
            System.out.println();
            return;
        }
        System.out.println(line);
    }
    //считывает обрабатывает отправляет и получает команды и выводит их в консоль
    public void readCommand(IOProvider provider) {
        System.out.print("Введите команду ( или help): ");
        String[] com;
        stacksize = 0;
        com = scanner.nextLine().split("\\s");
        String cname="";
        String arg ="";
        if (com.length > 0) {
            if (com[0] != null && com[0].equals("")) {
                System.out.println("команда не должна быть пустой");
                return;
            }
            else{
                cname=com[0];
                String[] args =new String[com.length-1];
                System.arraycopy(com,1,args,0,com.length-1);
                arg=String.join(" ",args);
            }
            Command command	= null;
            try {
                command = commandmanager.getCommand(cname);
                if(command == null){
                    System.out.println("Нет такой команды");
                    return;
                }
                if (command.getName().equals("save")) {
                    System.out.println("Клиент не может сохранять данные");
                    return;
                }

            }
            catch (CommandIOException e) {
                System.out.println("Введеная несуществующая команда");
                return;

            }

            try {

                CommandShallow shallow = new CommandShallow((String)command.getName(),arg);
                // Звесь встаить функцию проверки aргумерта
                if (!argCheck(cname,arg)){return;}
                System.out.println("command= "+command.getName()+" "+arg);

                if (((String) command.getName()).contains("execute_script")){
                    command.execute(arg);
                    return;
                }
                execute_command(shallow,provider,commandmanager);


            }
            catch (IllegalArgumentException | IOException e) {
                System.out.println(e.getMessage());
            }
            catch (InvalidArgsException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //остановка работы консоли
    public static void stop() {
        active = false;
    }

    public static void execute_command(CommandShallow shallow,IOProvider provider,CommandManager commandManager) throws IOException {
        if (shallow.getCommand().contains("add") || shallow.getCommand().equals("update") ) {
            try {
                StudyGroup sg= commandManager.getCommand(shallow.getCommand()).createSG(provider);
                shallow.setStudyGroup(sg);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject((Object) shallow);
            System.out.println("before send 2 :" + shallow.getCommand());
        }
        catch (Exception e){
            System.out.println("Error oos"+e.getMessage());
        }
        byte[] arr = baos.toByteArray();
        System.out.println("Передача "+arr.length+" байт.");
        sender.send(arr);
        Response response = null;
        try {
            response = reader.readResponse();
        }

        catch (IOException e){
            System.out.println("server is not avaliable: "+ e.getMessage());
        }

        catch (ClassNotFoundException e){
            System.out.println("smth went wrong...: "+ e.getMessage());
        }

        String s= new String(response.getMessage(), Charset.defaultCharset());

        if (response.getRnumber()>1){
            s="";
            ArrayList<Response> responses = new ArrayList<Response>();
            responses.add(response);
            int rcount=1;
            for(int i=1;i<response.getRnumber();i++){
                try {
                    responses.add(reader.readResponse());
                    rcount++;
                } catch (SocketTimeoutException | ClassNotFoundException e){
                    System.out.println("ReadSocket Timeout");
                }

            }


            if(rcount==response.getRnumber()){
                responses.sort(new Comparator<Response>() {
                    @Override
                    public int compare(Response o1, Response o2) {
                        return o1.getRcount() - o2.getRcount();
                    }
                });

                int message_length=0;
                for (int i=0;i<response.getRnumber();i++){
                    message_length += responses.get(i).getMessage().length;
                }
                ByteBuffer mbb = ByteBuffer.allocate(message_length);

                for (int i=0;i<response.getRnumber();i++){
                    mbb.put(responses.get(i).getMessage());
                };
                mbb.rewind();
                s= new String(mbb.array(),Charset.defaultCharset());
            }
            else {
                System.out.println("Ошибка приёма-передачи , повторите ");
            }

        }


        if (s.equals("exit")) {
            Console.stop();
        }
        System.out.println(s);


    }
    boolean argCheck(String name, String arg){
        if((name.equals("execute_script")||name.equals("count_less_than_group_admin")) && !arg.isEmpty()){
            //если имеется ввиду Person.name
            return true;
        }
        else if (name.equals("update")||name.equals("remove_by_id")) {
            try {
                Long.parseLong(arg);
            } catch (Exception e) {
                System.out.println("Значение аргумента не long");
                return false;
            }
            return true;
        }
        return true;
    }
}