package org.example.server;

import org.example.command.*;
import org.example.exception.CommandIOException;
import org.example.exception.InputArgumentException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.server.core.*;

import java.io.IOException;
import java.util.Scanner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Main {

    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InputArgumentException, IOException {
        logger.info("Запуск сервера");
        final  String FILENAME = System.getenv("FILENAME");
        CollectionManager collection = CollectionManager.fromFile(FILENAME);

        if (args.length != 0) {
            throw new InputArgumentException("Error! Got " + Integer.valueOf(args.length) + " arguments when 0 required");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            collection.save();
            logger.info("\nShutting down server");
        }));
        CommandManager commandmanager = new CommandManager();
        System.out.println("after commandmanager");
        UDPConnector connector = new UDPConnector();
        System.out.println("after UDPconnector");
        UDPReader reader = null;
        UDPSender sender = null;
        try {
            reader = new UDPReader(connector.getChannel(), connector.getSelector());
            sender = new UDPSender(connector.getChannel(), connector.getSelector());
        }
        catch (Exception e) {
            logger.error("Can't open UDP reader: {}", e.getMessage(), e);
        }


        String[] comnames = {"help", "info", "show", "add", "update", "remove_by_id", "clear", "save", "execute_script", "exit", "remove_at", "sort", "history", "sum_of_age", "print_field_ascending_character", "print_field_descending_character"};
        Command[] coms = {new HelpCommand(), new InfoCommand(), new ShowCommand(), new AddCommand(), new RemoveByIdCommand(), new ClearCommand(), new SaveCommand(), new ExecuteScriptCommand(), new ExitCommand(), new RemoveFirstCommand(),new AddIfMinCommand(), new CountLesAdminNameCommand(), new UpdateCommand()};
        for (int i = 0; i < coms.length; ++i)
        {
            try {
                commandmanager.createCommand(comnames[i], coms[i]);
            }
            catch (CommandIOException e) {
                logger.error(e.getMessage());
            }
        }
        UDPReader finalReader = reader;
        UDPSender finalSender = sender;
        //ошибка в цикле
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while(true){
                        try {
                            if(connector.getSelector().select()>0) {
                                logger.debug("In read");
                                finalReader.execute();
                                //SocketAddress client = reader.receive();
                                Response[] response = finalReader.getShallow().getCommand().execute(finalReader.getShallow().getArguments(), 0, finalReader.getShallow().getStudyGroup(), commandmanager, collection);
                                finalSender.send(response, finalReader.getClient(),connector.getChannel(),logger);
                                logger.debug("End of cycle");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }

                }
                catch (Exception e){System.out.println("Ошибка в цикле"+e.getMessage());}//null
            }}).start();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()){
            String serv_com = sc.nextLine();
            logger.info("Server command: {}", serv_com);
            if (serv_com.compareTo("save")==0){
                collection.save();
            }
            if (serv_com.compareTo("exit")==0){

                System.exit(0);
            }

        }

    }
}


