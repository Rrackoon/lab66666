package org.example.utils;

import org.example.interfaces.Printer;

import java.util.Scanner;

public class IOProvider {
    Printer printer;
    Scanner scanner;

    public IOProvider(Scanner scanner, Printer printer) {
        this.scanner = scanner;
        this.printer = printer;
    }


    public Scanner getScanner() {
        return scanner;
    }

    public void closeScanner() {scanner.close();}

    public Printer getPrinter() {
        return printer;
    }
}
