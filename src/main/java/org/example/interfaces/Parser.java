package org.example.interfaces;

public interface Parser {
    String parseString(String name, String descr);
    Integer parseInteger(String name, String descr);
    Float parseFloat(String name, String descr);
    Double parseDouble(String name, String descr);
    Long parseLong(String name, String descr);
    <T extends Enum<T>> T parseEnum(Class<T> enumType, String name, String descr) throws InterruptedException;
}
