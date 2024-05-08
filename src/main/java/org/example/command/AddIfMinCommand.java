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

public class AddIfMinCommand extends Command {
    public AddIfMinCommand() {
        super("add_if_max",
                "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
    }

    @Override
    public  Response[] execute(String args,  String studyGroup1, CommandManager commandmanager, CollectionManager collection)  {
        String[] response = new String[1];
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());//для корректной десериализации
        Gson gson = builder.create();
        StudyGroup studyGroup = gson.fromJson(studyGroup1, StudyGroup.class);
        String[] pids = CollectionManager.getCollection().stream().map(dr -> dr.getGroupAdmin().getPassportID().toString()).toArray(String[]::new);
        if( Arrays.asList(pids).contains(studyGroup.getGroupAdmin().getPassportID().toString())){
            response[0]="Нарушена уникальность passportID: "+studyGroup.getGroupAdmin().getPassportID();
        }
        StudyGroup minStudyGroup = collection.min();
        if (minStudyGroup.getStudentsCount() <= studyGroup.getStudentsCount()) {
            try {
                studyGroup.setId();
                collection.push(studyGroup);
                response[0] = "Элемент добавлен " + studyGroup.getName();
            } catch (Exception e) {
                response[0] = "Ошибка при добавлении элемента " + studyGroup.getName();

            }
        }
        else{
            response[0] = "Группа не добавлена. Минимальная группа  " + minStudyGroup.getStudentsCount();
        }
        Response[] respArr=Response.createResponses(response);
        return respArr;

    }

    @Override
    public void execute(String args) throws InvalidArgsException {

    }



}



