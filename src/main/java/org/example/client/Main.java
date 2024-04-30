package org.example.client;

import com.google.gson.JsonParseException;
import org.example.CLIPrinter;
import org.example.client.core.Console;
import org.example.client.core.UDPConnector;
import org.example.client.core.UDPReader;
import org.example.client.core.UDPSender;
import org.example.command.*;
import org.example.exception.CommandIOException;
import org.example.exception.InputArgumentException;
import org.example.interfaces.Printer;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.parser.CommandParser;
import org.example.utils.IOProvider;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.apache.logging.log4j.core.appender.rewrite.MapRewritePolicy.Mode.Add;

public class Main {

    public static void main(String[] args) throws InputArgumentException {
        if (args.length != 0) {
            throw new InputArgumentException("Error! Got " + Integer.valueOf(args.length) + " arguments when 0 required");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nВыключаем клиент");
        }));
        CommandManager commandmanager = new CommandManager();
        UDPConnector connector = new UDPConnector("localhost", 9999);
        UDPSender sender = new UDPSender(connector.getDatagramSocket(),connector.getServerAddress(),connector.getServerPort());
        UDPReader reader = new UDPReader(connector.getDatagramSocket());
        Console console = new Console(commandmanager, sender, reader);
        String[] comnames = {"help", "info", "show", "add", "update", "remove_by_id", "clear", "save", "execute_script", "exit", "add_if_min", "count_less_than_group_admin", "update","print_asceding","remove_first"};
        Command[] coms = {new HelpCommand(), new InfoCommand(), new ShowCommand(), new AddCommand(), new UpdateCommand(), new RemoveByIdCommand(), new ClearCommand(), new SaveCommand(), new ExecuteScriptCommand(), new ExitCommand(),new AddIfMinCommand(), new CountLesAdminNameCommand(), new UpdateCommand(),new PrintAscendingCommand(), new RemoveFirstCommand()};
        for (int i = 0; i < coms.length; ++i)
        {
            try {
                commandmanager.createCommand(comnames[i], coms[i]);
            }
            catch (CommandIOException e) {
                System.out.println(e.getMessage());
            }
        }
        console.start(connector);
    }
}
