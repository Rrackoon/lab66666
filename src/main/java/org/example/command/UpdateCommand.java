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

public class UpdateCommand extends Command {

    public UpdateCommand() {
        super("update", "обновить значение элемента коллекции, id которого равен заданному", 2);
    }
    @Override
    public  Response[] execute(String args,  String studyGroup1, CommandManager commandmanager, CollectionManager collection)  {
        GsonBuilder builder = new GsonBuilder();//создаем экземпляр
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());//для корректной десериализации
        Gson gson = builder.create();
        StudyGroup studyGroup = gson.fromJson(studyGroup1, StudyGroup.class);
        long id=Long.parseLong(args);
        studyGroup.setId();
        collection.update(id,studyGroup);
        String[] response = new String[2];
        response[0]="Изменена группа: "+studyGroup.getName();
        response[1]="c ID:: "+Long.parseLong(args);
        Response[] respArr = Response.createResponses(response);
        return  respArr;
    }



    @Override
    public void execute(String args) throws InvalidArgsException {
        validateArgs(args, 1);

        long studyGroupId = Long.parseLong(args);
        StudyGroup studyGroup = collection.get(studyGroupId);
        if (studyGroup == null) {
            provider.getPrinter().print("StudyGroup with specified ID doesn't exist.");
            return;
        }
        String line = "-".repeat(60);
        provider.getPrinter().print("Chosen StudyGroup:");
        provider.getPrinter().printf("%s\n%s\n%s\n", line, studyGroup, line);
        SGParser argParser = new SGParser(provider.getScanner(), provider.getPrinter());
        StudyGroup newStudyGroup = argParser.parseStudyGroup();
        collection.update(studyGroupId, newStudyGroup);
        provider.getPrinter().printf("StudyGroup (ID %s) updated successfully.\n", studyGroupId);
    }

}
