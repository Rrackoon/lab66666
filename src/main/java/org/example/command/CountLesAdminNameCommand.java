package org.example.command;
import org.example.managers.CommandManager;
import org.example.models.*;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.utils.IOProvider;

import java.nio.ByteBuffer;

public class CountLesAdminNameCommand extends Command {
    public CountLesAdminNameCommand() {
        super("count_less_than_group_admin",
                "вывести количество элементов, значение поля groupAdmin которых меньше заданного");
    }


    @Override
    public  Response[] execute(String args,  String studyGroup, CommandManager commandmanager, CollectionManager collection)  {
        String name = args; // Получение имени
        long count = collection.getCollection().stream()
                .filter(d -> d.getGroupAdmin() != null && name.compareTo(d.getGroupAdmin().getName()) > 0)
                .count();

        String[] response = { "Количество элементов, значение поля groupAdmin которых меньше заданного (" + name + "): " + count };
        Response[] respArr = Response.createResponses(response);
        return respArr;
    }

    @Override
    public void execute(String args) throws InvalidArgsException {

    }



}
