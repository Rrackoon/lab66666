package org.example.command;

import org.example.exception.ExitException;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.nio.ByteBuffer;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
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
