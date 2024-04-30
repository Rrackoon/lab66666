package org.example.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StudyGroup implements Serializable, Comparable<StudyGroup>{
    public static long ID = 0;//присваивание максимального ID
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long studentsCount; //Значение поля должно быть больше 0
    private int expelledStudents; //Значение поля должно быть больше 0

    private long shouldBeExpelled; //Значение поля должно быть больше 0, Поле может быть null

    private FormOfEducation formOfEducation; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup(String name, Coordinates coordinates, Long studentsCount, int expelledStudents, long shouldBeExpelled, FormOfEducation formOfEducation, Person groupAdmin) {
        this.id = ++ID;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate= LocalDateTime.now();
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.shouldBeExpelled = shouldBeExpelled;
        this.formOfEducation = formOfEducation;
        this.groupAdmin = groupAdmin;
    }
    public void setId(){
        this.id=++ID;
    }
    public void setId(long id){
        this.id=id;
    }
    public long getID(){return ID;}
    public long getId(){return id;}
    public String getName(){
        return name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public Long getStudentsCount(){
        return studentsCount;
    }
    public int getExpelledStudents(){
        return expelledStudents;
    }
    public long getShouldBeExpelled(){
        return shouldBeExpelled;
    }
    public String getCreationDate() {
        return creationDate.toString();
    }
    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (formOfEducation == null) return false;
        if (studentsCount <= 0) return false;
        if (expelledStudents <= 0) return false;
        if (shouldBeExpelled <=0 ) return false;
        return groupAdmin != null && groupAdmin.validate();
    }

    public void update(StudyGroup studyGroup){
        this.name = studyGroup.name;
        this.coordinates = studyGroup.coordinates;
        this.studentsCount = studyGroup.studentsCount;
        this.expelledStudents = studyGroup.expelledStudents;
        this.shouldBeExpelled = studyGroup.shouldBeExpelled;
        this.formOfEducation = studyGroup.formOfEducation;
        this.groupAdmin = studyGroup.groupAdmin;
    }

    public static boolean validateName(String name)  {

        return name != null ;
    }
    public static boolean validateStudentsCount(long studentsCount)  {

        return studentsCount > 1 ;
    }

    public static boolean validateExpelledStudents(int expelledStudents)  {

        return expelledStudents > 0 ;
    }
    public static boolean validateShouldBeExpelled(long shouldBeExpelled)  {

        return shouldBeExpelled > 0 ;
    }
    public static boolean validateFormOfEducation(FormOfEducation formOfEducation) {
        return (formOfEducation != null);}

    @Override
    public String toString() {
        return String.format("ID: %s\nName: %s\nCoordinates:\n%s\nCreation date: %s\nStudentsCount: %s\nExpelledStudents: %s\n" +
                        "ShouldBeExpelled: %s\nFormOfEducation: %s\nGroupAdmin: %s",
                id, name, coordinates, creationDate, studentsCount, expelledStudents, shouldBeExpelled, formOfEducation, groupAdmin);
    }

    @Override
    public int compareTo(StudyGroup studyGroup) {
        return Long.compare(this.getStudentsCount(), studyGroup.getStudentsCount());
    }
}
