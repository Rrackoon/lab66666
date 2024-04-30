package org.example.client.core;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import java.net.*;

import org.example.exception.CommandIOException;
import org.example.managers.*;
import org.example.command.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console implements Serializable {
    private Scanner scanner;
    private boolean active;
    private CommandManager commandmanager;
    private LinkedList<String> commandsStack;
    private int stacksize;
    private UDPSender sender;
    private UDPReader reader;

    public Console(CommandManager commandmanager, UDPSender sender, UDPReader reader)
    {
        this.scanner = new Scanner(System.in);
        this.active = true;
        this.commandmanager = commandmanager;
        this.commandsStack = new LinkedList<String>();
        this.stacksize = 0;
        this.sender = sender;
        this.reader = reader;
    }

    public void start(UDPConnector connector) {
        boolean con = false;
        String host = "";
        int port = 0;
        Pattern pattern = Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$");
        //connector.Connect(host,port);
        connector.Connect("localhost",9999);
        sender = new UDPSender(connector.getDatagramSocket(), connector.getServerAddress(),connector.getServerPort());
        reader =  new UDPReader(connector.getDatagramSocket());
        int[] parametersptr = {-1};
        CommandShallow shallow = new CommandShallow();
        String[] parameters = new String[0];
        while (this.active) {
            readCommand();
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

    public void readCommand() {
        System.out.print("Введите команду ( или help): ");
        String[] com;
        stacksize = 0;
        com = scanner.nextLine().split(" ");
        if (com.length > 0) {
            if (com[0] != null && com[0].equals("")) {
                System.out.println("команда не должна быть пустой");
                return;
            }
            Command command	= null;
            try {
                command = commandmanager.getCommand(com[0]);
                if (command != null && command.getName().equals("save")) {
                    System.out.println("Клиент не может сохранять данные");
                    return;
                }

            }
            catch (CommandIOException e) {
                System.out.println(e.getMessage());
            }

            try {
                CommandShallow shallow = new CommandShallow(command,com);
                System.out.println("command= "+command.getName());
                if (command.getName().equals("add {element}") || command.getName().equals("update id {element}")) {
                    String[] advices = command.getParameterAdvices();
                    String[] parameters = new String[advices.length];
                    for (int i = 0; i < advices.length; ++i) {
                        this.print(advices[i]);
                        boolean ok = false;
                        while (!ok) {
                            parameters[i] = scanner.nextLine();
                            ok = true;
                            if (parameters[i].split(" ").length > 1 && i != 0) {
                                System.out.println("Требуется ввести только одно значение!");
                                ok = false;
                            }
                            if (i != 0) {
                                parameters[i] = parameters[i].split(" ")[0];
                            }
                            if ((i == 0 || i == 1 || i == 2 || i == 3 || i == 7) && (parameters[i].equals(""))) {
                                System.out.println("Переменная не может иметь значение null!");
                                ok = false;
                            }
                            if (ok) {
                                try {
                                    switch(i) {
                                        case 1: {
                                            int x = Integer.parseInt(parameters[i]);
                                            if (x <= -32) {
                                                ok = false;
                                                System.out.println("X должен быть больше -522");
                                            }
                                        }
                                        break;
                                        case 2:
                                            int y = Integer.parseInt(parameters[i]);
                                            if (y <= -32) {
                                                ok = false;
                                                System.out.println("Y должен быть больше -345");
                                            }
                                        case 3: {
                                            int x = Integer.parseInt(parameters[i]);
                                            if (x <= 0) {
                                                ok = false;
                                                System.out.println("Количество студентов должно быть больше 0");
                                            }
                                        }
                                        break;
                                        case 4: {
                                            int x = Integer.parseInt(parameters[i]);
                                            if (x <= 0) {
                                                ok = false;
                                                System.out.println("Количество исключенных студентов должно быть больше 0");
                                            }
                                        }
                                        break;
                                        case 5: {
                                            int x = Integer.parseInt(parameters[i]);
                                            if (x <= 0) {
                                                ok = false;
                                                System.out.println("Количество тех, кто должен быть исключен должно быть больше 0");
                                            }

                                        }
                                        break;
                                        case 6:
                                            if (!parameters[i].equals("") && !parameters[i].equals("DISTANCE_EDUCATION") && !parameters[i].equals("FULL_TIME_EDUCATION") && !parameters[i].equals("EVENING_CLASSES")) {
                                                ok = false;
                                                System.out.println("Введено неверное значение");
                                            }
                                            break;
                                        case 7:
                                            if(parameters[i].equals(null)){
                                                ok = false;
                                                System.out.println("Имя не может быть пустое");
                                            }
                                            break;
                                        case 8:
                                            int x = Integer.parseInt(parameters[i]);
                                            if (x <= 0) {
                                                ok = false;
                                                System.out.println("id паспорта не может быть 0");

                                            }
                                            break;
                                        case 9:
                                            if (!parameters[i].equals("") && !parameters[i].equals("GREEN") && !parameters[i].equals("YELLOW") && !parameters[i].equals("ORANGE") && !parameters[i].equals("WHITE")&& !parameters[i].equals("BLACK")&& !parameters[i].equals("BLUE")) {
                                                ok = false;
                                                System.out.println("Введено неверное значение");
                                            }
                                            break;
                                        case 10:{
                                            int X = Integer.parseInt(parameters[i]);
                                            if (X <= -32) {
                                                ok = false;
                                                System.out.println("X должен быть больше -52");
                                            }
                                        }
                                        break;
                                        case 11: {
                                            int Y = Integer.parseInt(parameters[i]);
                                            if (Y <= -32) {
                                                ok = false;
                                                System.out.println("X должен быть больше -32");
                                            }
                                        }
                                        break;
                                        case 12: {
                                            if(parameters[i].equals("")) {

                                                ok = false;
                                                System.out.println("Имя не может ьыть пусто");
                                            }
                                        }
                                        break;
                                    }
                                }
                                catch (Exception e) {
                                    System.out.println("Введено неверное значение");
                                    ok = false;
                                }


                            }
                        }
                    }
                    try {
                        shallow.setStudyGroup(parameters);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject((Object) shallow);
                    System.out.println("before send 2 :" + shallow.getCommand().getName());
                }
                catch (Exception e){
                    System.out.println("Error oos"+e.getMessage());
                }
                byte[] arr = baos.toByteArray();
                System.out.println("before send ");
                sender.send(arr);
                Response response = null;
                System.out.println("before readResp1");
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
                    ArrayList<Response> responses = new ArrayList<Response>();
                    responses.set(0, response);
                    int rcount=1;
                    for(int i=1;i<response.getRnumber();i++){
                        try {
                            responses.set(i,reader.readResponse());
                            rcount++;
                        } catch (SocketTimeoutException | ClassNotFoundException e){
                            System.out.println("ReadSocket Timeout");
                        }

                    }
                    if(rcount==response.getRnumber()){
                        //всё норм
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

                }


                    if (s.equals("exit")) {
                        this.stop();
                    }
                    System.out.println(s);

            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        active = false;
    }

}