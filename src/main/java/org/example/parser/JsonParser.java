package org.example.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import org.example.models.StudyGroup;
import org.example.utils.LocalDateTimeDeserializer;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Scanner;

public class JsonParser {
    public static StudyGroup[] parseFile(FileReader file) throws JsonParseException {
        GsonBuilder builder = new GsonBuilder();//создаем экземпляр
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());//для корректной десериализации
        Gson gson = builder.create();
        StringBuilder inputData = new StringBuilder();//для хранения считанных данных из файла
        Scanner scanner = new Scanner(file);//для чтения данных из файла
        //разбор строки и запись данных
        while (scanner.hasNext()) {
            inputData.append(scanner.nextLine());
        }
        return gson.fromJson(inputData.toString(), StudyGroup[].class);
    }
}
