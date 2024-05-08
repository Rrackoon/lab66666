package org.example.command;

import com.google.gson.Gson;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;

import java.nio.ByteBuffer;

import org.example.utils.ByteActions;
import org.example.utils.ByteActions.*;
import org.example.utils.IOProvider;

public class ShowCommand extends Command {

    public ShowCommand() {
        super("show", "вывести все элементы коллекции в строковом представлении");
    }


    @Override
    public  Response[] execute(String args,  String studyGroup1, CommandManager commandmanager, CollectionManager collection)  {
        //  ByteBuffer respBuff = ByteBuffer.wrap("".getBytes());
        Gson gson = new Gson();
        StudyGroup studyGroup = gson.fromJson(studyGroup1, StudyGroup.class);
        String[] response = collection.getCollection().stream().map(dr -> dr.toString()).toArray(String[]::new);

        Response[] respArr= Response.createResponses(response);

        return  respArr;
    }

    @Override
    public void execute(String args) throws InvalidArgsException {

    }




}
