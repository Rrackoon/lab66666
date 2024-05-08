package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.nio.ByteBuffer;

public class RemoveFirstCommand extends Command {
    public RemoveFirstCommand() {
        super("remove_first", "удалить первый элемент из коллекции");
    }


    @Override
    public  Response[] execute(String args,  String  studyGroup, CommandManager commandmanager, CollectionManager collection)  {
        String[] response = new String[1];
        try {
            collection.remove(1);
            response[0]="Элемент удален";
        }
        catch (Exception e) {
           response[0] = "Ошибка при удалении элемента ";
        }
        Response[] respArr=Response.createResponses(response);
        return respArr;
    }

    @Override
    public void execute(String args) throws InvalidArgsException {

    }


}
