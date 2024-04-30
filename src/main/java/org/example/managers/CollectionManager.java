package org.example.managers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.StudyGroup;
import org.example.parser.JsonParser;
import org.example.utils.LocalDateTimeSerializer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public  class CollectionManager {
    private static  LinkedList<StudyGroup> collection;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final String fileName;

    private CollectionManager(String fileName) {
        collection= new LinkedList<StudyGroup>();
        this.fileName = fileName;
    }

    public static LinkedList<StudyGroup> getCollection() {
        return collection;
    }

    //присваивание max id
    public static CollectionManager fromFile(String fileName) throws FileNotFoundException {
        CollectionManager collection = new CollectionManager(fileName);
        StudyGroup[] studyGroups = JsonParser.parseFile(new FileReader(fileName));
        for (StudyGroup studyGroup : studyGroups) {
            if (studyGroup.getId() > StudyGroup.ID) {
                StudyGroup.ID = studyGroup.getId();
            }
            collection.push(studyGroup);
        }

        return collection;
    }

    public void push(StudyGroup element) {
        if (element.validate() && get(element.getId()) == null) {
            collection.push(element);
        }
    }

    public void save() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = builder.create();

        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(collection.toArray(), StudyGroup[].class, writer);
            System.out.println("Коллекция сохранена в файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении коллекции в файл: " + e.getMessage());
        }
    }
    public void remove(int id) {
        try {
            boolean found = false;
            for (int i = 0; i < collection.size(); ++i) {
                if (collection.get(i).getId() == id) {
                    collection.remove(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IOException("Error! Dragon with ID " + id + " not found");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void update(long id, StudyGroup newStudyGroup) {
        StudyGroup studyGroup = get(id);
        studyGroup.update(newStudyGroup);
    }

    public void removeById(long id) {
        collection.remove(get(id));
    }

    public void clear() {
        collection.clear();
    }


    public StudyGroup get(long id) {
        for (StudyGroup studyGroup : collection)
            if (studyGroup.getId() == id)
                return studyGroup;
        return null;
    }



    public String description() {
        return String.format("Тип: %s\nДата инициализации: %s\nКол-во элементов: %s",
                collection.getClass().getName(), createdAt, collection.size());
    }

    public String getFileName() {
        return this.fileName;
    }

    public String dump() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        Gson gson = builder.create();
        return gson.toJson(collection.toArray(), StudyGroup[].class);
    }


    public StudyGroup min() {
        if (!collection.isEmpty()) {
            return collection.stream()
                    .min(StudyGroup::compareTo)
                    .get();
        } else {
            return null;
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (StudyGroup studyGroup : collection) {
            builder.append(studyGroup);
            builder.append("\n");
        }
        return builder.toString();
    }


}
