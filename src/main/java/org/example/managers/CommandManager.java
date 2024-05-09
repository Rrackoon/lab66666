package org.example.managers;
import org.example.exception.CommandIOException;
import org.example.utils.IOProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;
import org.example.models.StudyGroup;
import org.example.models.Person;
import org.example.utils.IOProvider;
import org.example.command.*;
public class CommandManager {

    private static final Map<String, Command> commands = new HashMap<>();
    private IOProvider provider;
    public CommandManager(IOProvider provider) {
        this.provider=provider;
    }


    public CommandManager() {
    }
    public String getCommands() {
        String cmd = "";
        for (Map.Entry<String, Command> val : commands.entrySet()) {
            cmd += val.getValue().getName() + " : " + val.getValue().getDescription() + "\n";
        }
        return cmd;
    }

    public void createCommand(String name, Command command) throws CommandIOException {
        if (name.equals(null) || name.equals("^\\s*$")) {
            throw new CommandIOException("Error! Can't create command with name \"" + name + "\"");
        }
        commands.put(name, command);
    }


    public boolean execute(String commandName, String args) throws InvalidArgsException {
        if (commands.containsKey(commandName)) {
            commands.get(commandName).execute(args);
            return true;
        }
        return false;
    }

    public Command getCommand(String name) throws CommandIOException{
        if (commands.containsKey(name)) {
            return commands.get(name);
        }
        throw new CommandIOException("Error! Unknown command \"" + name + "\"");
    }
}

