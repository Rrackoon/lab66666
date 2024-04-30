package org.example.models;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private int x; //Значение поля должно быть больше -151, Поле не может быть null
    private  long y; //Значение поля должно быть больше -436

    public Coordinates(int x, long y){
        this.x=x;
        this.y=y;
    }
    public static boolean validateX(int x){
        return (x>-151);
    }
    public static boolean validateY(long y){
        return (y>-436);
    }
    public boolean validate(){
        return (validateX(this.x) && validateY(this.y));
    }
    public Coordinates update(Coordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
        return this;
    }

    @Override
    public String toString() {
        return String.format("---- X: %s\n---- Y: %s", x, y);
    }
}

