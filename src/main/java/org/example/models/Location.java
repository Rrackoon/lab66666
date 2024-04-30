package org.example.models;

import java.io.Serializable;

public class Location implements Serializable {
    private Integer x;
    private Integer y;
    private String name; //Поле может быть null

    public Location(Integer x, Integer y,String name){
        this.x=x;
        this.y=y;
        this.name=name;
    }
    public static boolean validateX(Integer x){
        return x!= null;
    }
    public static boolean validateY(Integer y){
        return y != null;
    }
    public static boolean validateName(String  name){
        return name != null;
    }
    public boolean validate(){
        return (validateX(this.x) &&
                validateY(this.y) &&
                validateName(this.name));
    }
    public Location  update(Location location) {
        this.x = location.x;
        this.y = location.y;
        this.name = location.name;
        return this;
    }

    @Override
    public String toString() {
        return String.format("----- X: %s\n----- Y: %s\n----Name: %s", x, y, name);
    }
}