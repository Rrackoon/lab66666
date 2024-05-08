package org.example.command;

import org.example.CLIPrinter;
import org.example.client.core.Console;
import org.example.exception.InvalidArgsException;
import org.example.interfaces.Printer;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.parser.CommandParser;
import org.example.utils.IOProvider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {
    private final int recDepth;
    public ExecuteScriptCommand() {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.recDepth = 1;


    }
    public ExecuteScriptCommand(IOProvider provider, CollectionManager collection, int recDepth) {
        super("execute_script", "считать и исполнить скрипт из указанного файла", provider, collection);
        this.recDepth = recDepth;
    }


    @Override
    public Response[] execute(String args, String studyGroup, CommandManager commandmanager, CollectionManager collection) {
        return new Response[0];
    }

    @Override
    public void execute(String args) throws InvalidArgsException {
        if (!validateArgs(args,2)){return;}
        String fileName = args;

        try (FileReader fileReader = new FileReader(fileName)) {//открывается файл + создается экземпляр
            var provider = new IOProvider(new Scanner(fileReader), new CLIPrinter(),true);
            var commandManager = new CommandManager( provider);
            var commandParser = new CommandParser(commandManager, provider, recDepth + 1);
            commandParser.run();
        } catch (FileNotFoundException e) {
            System.out.println("File not found or access denied (read).");
            System.out.println("Нет такого файла");
            return;
        } catch (IOException e) {
            System.out.println("Something went wrong while reading.");
        }
    }


}