package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.nio.ByteBuffer;

public class RemoveByIdCommand extends Command {
    public RemoveByIdCommand() {
        super("remove_by_id", "удалить элемент из коллекции по его id");
    }


    @Override
    public boolean validateArgs(String args, int length)  {
        try {
            super.validateArgs(args, length);
            long id = Long.parseLong(args);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Ошибка формата аргумента");
            return false;
        }
        return true;
    }

    @Override
    public Response[] execute(String args, String studyGroup, CommandManager commandmanager, CollectionManager collection) {
        String[] response = new String[1];

        try{
            collection.removeById(Integer.parseInt(args));
            response[0] = "Удалена группа: ID"+args  ;
        } catch(Exception e){
            response[0] = "Ошибка при удалении группы ID"+args ;

        }
        Response[] respArr=Response.createResponses(response);
        return respArr;


    }
    @Override
    public void execute(String args) throws InvalidArgsException {

    }


}
