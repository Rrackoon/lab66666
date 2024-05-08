package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.nio.ByteBuffer;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear", "очистить коллекцию");
    }


    @Override
    public  Response[] execute(String args, String studyGroup, CommandManager commandmanager, CollectionManager collection)  {
        String[] response = new String[1];
        collection.clear();
        response[0] = "Коллекция очищена";
        Response[] respArr=Response.createResponses(response);
        return respArr;
    }

    @Override
    public void execute(String args) throws InvalidArgsException {

    }

}
