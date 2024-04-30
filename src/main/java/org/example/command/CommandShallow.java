package org.example.command;

import org.example.models.StudyGroup;
import org.example.models.Color;
import org.example.models.Location;
import org.example.models.Person;
import org.example.models.Coordinates;
import org.example.models.FormOfEducation;

import java.io.Serializable;

public class CommandShallow implements Serializable {
    private Command command;
    private String[] args;
    private StudyGroup studyGroup;

    public CommandShallow() {
        this.command = null;
        this.args = null;
        this.studyGroup = null;
    }

    public CommandShallow(Command command, String[] args) {
        this.command = command;
        this.args = args;
        this.studyGroup = null;
    }


    public Command getCommand() {
        return command;
    }

    public String[] getArguments() {
        return args;
    }


    public void setStudyGroup(String[] splitted) {
 /*       Color col = null;
        switch(splitted[4]) {
            case "RED":
                col = Color.RED;
                break;
            case "BLACK":
                col = Color.BLACK;
                break;
            case "BLUE":
                col = Color.BLUE;
                break;
            case "YELLOW":
                col = Color.YELLOW;
                break;
            case "BROWN":
                col = Color.BROWN;
                break;
            default:
                throw new IllegalArgumentException("Error! Unknown color \"" + splitted[4] + "\"");
        }

        FormOfEducation education = null;
        switch(splitted[8]) {
            case "DISTANCE_EDUCATION":
                education = FormOfEducation.DISTANCE_EDUCATION;
                break;
            case "FULL_TIME_EDUCATION":
                education = FormOfEducation.FULL_TIME_EDUCATION;
                break;
            case "EVENING_CLASSES":
                education = FormOfEducation.EVENING_CLASSES;
                break;
            default:
                throw new IllegalArgumentException("Error! Unknown form of education \"" + splitted[8] + "\"");
        }

        Location location = new Location(Integer.parseInt(splitted[10]), Integer.parseInt(splitted[11]), splitted[12]);
        if (!location.validate()) {
            throw new IllegalArgumentException("Error! Invalid location");
        }

        Person groupAdmin = new Person(splitted[13], splitted[14], null, location);
        if (!groupAdmin.validate()) {
            throw new IllegalArgumentException("Error! Invalid group admin");
        }
*/
        this.studyGroup = new StudyGroup(
                splitted[0],  //имя группы
                new Coordinates(
                        Integer.parseInt(splitted[1]),//"Координата Х ",
                        Long.parseLong(splitted[2])),//"Координата y ",
                Long.parseLong(splitted[3]), // Количество студентов
                Integer.parseInt(splitted[4]),//Кол-во исключенных студентов
                Long.parseLong(splitted[5]),//Кол-во тех, кто должен быть исключен
                FormOfEducation.valueOf(splitted[6]),//Форма обучения
               new Person(splitted[7],//Имя админа группы
                       splitted[8],//id паспорта
                       Color.valueOf(splitted[9]),//Цвет волос
                        new Location(Integer.parseInt(splitted[10]), //"Координата Х ",
                                Integer.parseInt(splitted[11]), //"Координата  "
                                        splitted[12])));//Имя локаци

    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }
}
