package org.example.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.parser.SGParser;
import org.example.utils.IOProvider;
import org.example.utils.LocalDateTimeDeserializer;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Arrays;

public class AddCommand extends Command {

    public AddCommand() {
        super("add", "добавить новый элемент в коллекцию");

    }


    @Override
    public Response[] execute(String args,  String studyGroup1, CommandManager commandmanager, CollectionManager collection) {
        GsonBuilder builder = new GsonBuilder();//создаем экземпляр
        //builder.setPrettyPrinting();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());//для корректной десериализации
        Gson gson = builder.create();
        StudyGroup studyGroup = gson.fromJson(studyGroup1, StudyGroup.class);
        String[] pids = CollectionManager.getCollection().stream().map(dr -> dr.getGroupAdmin().getPassportID().toString()).toArray(String[]::new);
        String[] response = new String[1];
        if( Arrays.asList(pids).contains(studyGroup.getGroupAdmin().getPassportID().toString())){
            response[0]="Нарушена уникальность passportID: "+studyGroup.getGroupAdmin().getPassportID();
        }
        else {
            studyGroup.setId();
            collection.push(studyGroup);
            response[0] = "Добавлена группа: " + studyGroup.getName();
        }
        Response[] respArr = Response.createResponses(response);

        return respArr;
    }
    @Override
    public void execute(String args) throws InvalidArgsException {

        //обработка конца файла

    }



}
