package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("save", "сохранить коллекцию в файл");
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
