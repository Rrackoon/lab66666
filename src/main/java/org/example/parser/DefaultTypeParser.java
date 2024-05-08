package org.example.parser;

import org.example.exception.InterruptCommandException;
import org.example.interfaces.Parser;
import org.example.interfaces.Printer;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class DefaultTypeParser implements Parser {
    private final Scanner input;
    private final Printer printer;

    public DefaultTypeParser(Scanner scanner, Printer printer) {
        this.input = scanner;
        this.printer = printer;
    }
    //для ввода(образец)
    public String parseString(String name, String descr) throws NoSuchElementException {
        if (descr == null || descr.equals("")) {
            printer.printf("\nEnter %s:\n", name);
        } else {
            printer.printf("\nEnter %s (%s):\n", name, descr);
        }
        String line = input.nextLine().strip();

        if (line.equals("exit")) {
            throw new InterruptCommandException();
        }
        return line.equals("") ? null : line;
    }
    //считывает строку с консоли в типы разные
    public Integer parseInt(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);

                return Integer.parseInt(line) ;
            } catch (NumberFormatException exception) {
                printer.printf("Invalid %s.%n", name);
            }
        }
    }
    public Integer parseInteger(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return Integer.parseInt(line) ;
            } catch (NumberFormatException exception) {
                printer.printf("Invalid %s.%n", name);
            }
        }
    }
    public Long parseLong(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return Long.parseLong(line);
            } catch (NumberFormatException exception) {
                printer.printf("Invalid %s.%n", name);
            }
        }
    }

    public Float parseFloat(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return Float.parseFloat(line);
            } catch (NumberFormatException exception) {
                printer.printf("Invalid %s.%n", name);
            }
        }
    }

    public Double parseDouble(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return Double.parseDouble(line);
            } catch (NumberFormatException exception) {
                printer.printf("Invalid %s.%n", name);
            }
        }
    }

    public <T extends Enum<T>> T parseEnum(Class<T> enumType, String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return (line != null) ? Enum.valueOf(enumType, line) : null;
            } catch (NullPointerException | IllegalArgumentException exception) {
                printer.printf("Invalid %s.%n", name);
            }
        }
    }

    public void print(String text) {
        printer.print(text);
    }

}
