package org.example.models;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable, Comparable<Person> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Значение этого поля должно быть уникальным, Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(String name, String passportID, Color hairColor, Location location){
        this.name=name;
        this.passportID=passportID;
        this.hairColor=hairColor;
        this.location=location;
    }
    public static boolean validateName(String name){
        return (name!=null);
    }
    public static boolean validatePID(String passportID){
        return( passportID!=null);
    }
    public static boolean validateColor( Color hairColor){
        return (hairColor!=null);
    }
    public static boolean validateLocation(Location location){
        return (location!=null);
    }

    public boolean validate(){
        return(validateName(this.name) && validatePID(this.passportID) && validateColor(this.hairColor) && validateLocation(this.location));
    }
    public String getName(){
        return name;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.passportID, this.hairColor, this.location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return passportID.equals(person.passportID) &&
                name.equals(person.name) &&
                hairColor.equals(person.hairColor)&&
                location.equals(person.location);
    }

    @Override
    public String toString() {
        return String.format("---- Name: %s\n---- PasspostID: %s\n---- hairColor: %s\n---- location:\n%s",
                name, passportID,hairColor, location);
    }
    @Override
    public int compareTo(Person person) {
        return this.name.compareTo(person.name);
    }
}
