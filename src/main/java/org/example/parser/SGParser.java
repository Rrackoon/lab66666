package org.example.parser;
import org.example.interfaces.Printer;
import org.example.models.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.example.managers.CollectionManager;
import org.example.utils.IOProvider;


public class SGParser extends DefaultTypeParser {
    /**
     * Конструктор класса SGParser.
     *
     * @param scanner объект Scanner для считывания ввода.
     * @param printer объект Printer для вывода сообщений.
     */
    boolean printValue=false;
    public SGParser(Scanner scanner, Printer printer) {
        super(scanner, printer);
    }
    public SGParser(Scanner scanner, Printer printer,boolean printValue)
    {
        super(scanner, printer);
        this.printValue=printValue;
    }

    /**
     * Метод для парсинга объекта типа Person.
     *
     * @return объект Person.
     */
    public Person parsePerson() {
        print("PERSON:");
        // Person Name
        String name;
        while (!Person.validateName(name = parseString("Name", "not null"))) print("Invalid Name.");
        if (printValue){print(name);}
        String passportID;
        while (!Person.validatePID(passportID = parseString("passportID", "not null,")) ) print("Invalid passportID");
        if(printValue){print(passportID);}
        //Person hairColor
        Color hairColor;
        String colorValues = Arrays.asList(Color.values()).toString();
        while (!Person.validateColor(hairColor = parseEnum(Color.class, "Color " + colorValues, "not null"))) {
            print("Invalid Color " + colorValues);
        }
        if(printValue){print(hairColor.name());}

        //Person location
        Location location = parseLocation();
                return new Person(name, passportID,hairColor,location );

    }
    /**
     * Метод для парсинга объекта типа Location.
     *
     * @return объект Location.
     */
    public Location parseLocation() {
        print("LOCATION:");
        // Location X
        Integer x;
        while (!Location.validateX(x = parseInteger("X", "not null"))) print("Invalid X.");
        if(printValue){print(""+x);}
        // Location Y
        Integer y;
        while (!Location.validateY(y = parseInteger("Y", "not null,Integer"))) print("Invalid Y.");
        if(printValue){print(""+y);}
        //Location name
        String name;
        while (!Location.validateName(name = parseString("Name", "not null"))) print("Invalid Name.");
        return new Location(x, y, name);
    }
    /**
     * Метод для парсинга объекта типа Coordinates.
     *
     * @return объект Coordinates.
     */
    public Coordinates parseCoordinates() {
        print("COORDINATES:");
        // Coordinate X
        int x;
        while (!Coordinates.validateX(x = parseInt("X", "not null, long, min -951"))) print("Invalid X.");
        if(printValue){print(""+x);}
        // Coordinate Y
        long y;
        while (!Coordinates.validateY(y = parseLong("Y", "not null, float, max 779"))) print("Invalid Y.");
        return new Coordinates(x, y);
    }
    /**
     * Метод для парсинга объекта типа StudyGroup.
     *
     * @return объект StudyGroup.
     */
    public StudyGroup parseStudyGroup() {
        print("STUDY_GROUP:");
        // ST Name
        String name;
        while (!StudyGroup.validateName(name = parseString("Name", "not null, not empty"))) print("Invalid Name.");
        if(printValue){print(name);}
        // ST Coordinates
        Coordinates coordinates = parseCoordinates();
        // long studentsCount
        long studentsCount;
        while (!StudyGroup.validateStudentsCount(studentsCount = parseLong("StudentsCount", "long, min 1"))) {
            print("Invalid StudentsCount");
        }
        if(printValue){print(""+studentsCount);}
        //int expelledStudents
        int expelledStudents;
        while (!StudyGroup.validateExpelledStudents(expelledStudents = parseInt("ExpelledStudents", "int, min 1"))) {
            print("Invalid ExpelledStudents");
        }
        //long shouldBeExpelled
        long shouldBeExpelled;
        while (!StudyGroup.validateShouldBeExpelled(shouldBeExpelled = parseLong("ShouldBeExpelled", "long, min 1"))) {
            print("Invalid StudentsCount");
        }
        //FormOfEducation formOfEducation
        FormOfEducation formOfEducation;
        String formOfEducationValues = Arrays.asList(FormOfEducation.values()).toString();
        while (!StudyGroup.validateFormOfEducation(formOfEducation = parseEnum(FormOfEducation.class, "FormOfEducation " + formOfEducationValues, "not null"))) {
            print("Invalid FormOfEducation " + formOfEducationValues);
        }
        if(printValue){print(formOfEducationValues);}

        Person groupAdmin = parsePerson();

        return new StudyGroup(name, coordinates, studentsCount,
                expelledStudents,shouldBeExpelled,formOfEducation,groupAdmin);
    }
}
