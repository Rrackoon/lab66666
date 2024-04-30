package org.example.command;

import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.models.StudyGroup;
import org.example.utils.IOProvider;

import java.io.Serializable;

public abstract class Command  implements Serializable {
    String name;
    String description;
    IOProvider provider;
    int argsnumber;
    public int calls;
    CollectionManager collection;
    String[] parametersAdvices;
    public Command(String name, String description, int argsnumber, String[] parametersAdvices) {
        this.name = name;
        this.description = description;
        this.argsnumber = argsnumber;
        this.parametersAdvices = parametersAdvices;
        this.calls = 1;
    }


    public Command(String name, String description, IOProvider provider, CollectionManager collection,  String[] parametersAdvices) {
        this.name = name;
        this.description = description;
        this.provider = provider;
        this.collection = collection;
        this.parametersAdvices = parametersAdvices;
    }

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void checked(String[] args) throws InvalidArgsException {
        validateArgs(args, args.length);
    }

    public abstract Response[] execute(String[] args, Integer stacksize, StudyGroup studyGroup, CommandManager commandmanager, CollectionManager collection);

    public abstract void execute(String[] args) throws InvalidArgsException;

    public  String getDescription() {
        return String.format("%s    |     %s", name, description);
    }
    public void validateArgs(String[] args, int length) throws InvalidArgsException {
        if (args.length != length) {
            throw new InvalidArgsException();
        }
    }
    public String[] getParameterAdvices() {
        return parametersAdvices;
    }
    public Object getName() {
        return name;
    }
}
