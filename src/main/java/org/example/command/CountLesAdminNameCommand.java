package org.example.command;
import org.example.managers.CommandManager;
import org.example.models.*;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.utils.IOProvider;

import java.nio.ByteBuffer;

public class CountLesAdminNameCommand extends Command {
    public CountLesAdminNameCommand() {
        super("count_less_than_group_admin {name}",
                "вывести количество элементов, значение поля groupAdmin которых меньше заданного");
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
