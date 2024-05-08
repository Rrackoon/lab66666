package org.example.command;
import org.example.managers.CommandManager;
import org.example.models.*;
import org.example.exception.InvalidArgsException;
import org.example.managers.CollectionManager;

public class PrintAscedingCommand extends Command {
    public PrintAscedingCommand() {
        super("print_asceding", "вывести все элементы коллекции по возрастанию");
    }


    @Override
    public  Response[] execute(String args,  String studyGroup, CommandManager commandmanager, CollectionManager collection)  {
        String[] response = collection.getCollection().stream().map(StudyGroup :: getStudentsCount).sorted().map(ch -> ch.toString()).toArray(String[]::new);
        Response[] respArr= Response.createResponses(response);
        return respArr;

    }

    @Override
    public void execute(String args) throws InvalidArgsException {

    }


}
