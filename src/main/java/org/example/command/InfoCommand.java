package org.example.command;


import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.nio.ByteBuffer;

public class InfoCommand extends Command {
    public InfoCommand() {
        super("info", "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }


    @Override
    public  Response[] execute(String args,  String studyGroup, CommandManager commandmanager, CollectionManager collection)  {


        String[] response = new String[3];
        response[0] = "Коллекция: "+collection.getCollection().getClass().getName();
        response[1] =  "количество групп: "+collection.getCollection().size();
        response[2] =  "Дата создания: "+collection.getCreatedAt();
        Response[] respArr= Response.createResponses(response);
        return  respArr;
    }

    @Override
    public void execute(String args) throws InvalidArgsException {

    }



}
