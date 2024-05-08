package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.parser.SGParser;
import org.example.utils.IOProvider;

import java.io.Serializable;

public abstract class Command  implements Serializable {
    String name;
    String description;
    IOProvider provider;
    int argsnumber;
    public int calls;
    CollectionManager collection;
   // String[] parametersAdvices;
    public Command(String name, String description, int argsnumber) {
        this.name = name;
        this.description = description;
        this.argsnumber = argsnumber;
   //     this.parametersAdvices = parametersAdvices;
        this.calls = 1;
    }


    public Command(String name, String description, IOProvider provider, CollectionManager collection) {
        this.name = name;
        this.description = description;
        this.provider = provider;
        this.collection = collection;
   //     this.parametersAdvices = parametersAdvices;
    }
    public Command(String name, String description, IOProvider provider) {
        this.name = name;
        this.description = description;
        this.provider = provider;
    }

    public void setProvider(IOProvider provider) {
        this.provider = provider;
    }

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void checked(String args) throws InvalidArgsException {
        validateArgs(args, args.length()>0?1:0);
    }

    public abstract Response[] execute(String args, String studyGroup, CommandManager commandmanager, CollectionManager collection);

    public abstract void execute(String args) throws InvalidArgsException;



    public StudyGroup createSG(IOProvider provider)
    {
        SGParser parser = new SGParser(provider.getScanner(), provider.getPrinter(),provider.isPrintValue());
        return parser.parseStudyGroup();
    }

    public  String getDescription() {
        return String.format("%s    |     %s", name, description);
    }
    public boolean validateArgs(String args, int length)  {
        if (length >1 && args.compareTo("")==0) {
            System.out.println("Отсутствие аргумента команды");
            return false;
        }
        return true;
    }
    //public String[] getParameterAdvices() {
    //    return parametersAdvices;
    //}
    public Object getName() {
        return name;
    }


}
