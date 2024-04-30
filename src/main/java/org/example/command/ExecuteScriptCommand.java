package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.interfaces.Printer;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.managers.ScriptReader;
import org.example.models.StudyGroup;
import org.example.parser.CommandParser;
import org.example.utils.IOProvider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {
    private final int recDepth;
    public ExecuteScriptCommand() {
        super("execute_script {file_name}", "считать и исполнить скрипт из указанного файла");
        recDepth = 1;
    }




    @Override
    public  Response[] execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection)  {
        ByteBuffer respBuff = ByteBuffer.wrap("".getBytes());
        String[] response = collection.getCollection().stream().map(dr -> dr.toString()).toArray(String[]::new);

        Response[] respArr= Response.createResponses(response);

        return  respArr;
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {

    }
}