package com.team13.trojancheckin_out.UPC;

import com.team13.trojancheckin_out.Accounts.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.team13.trojancheckin_out.Database.BuildingManipulator.referenceBuildings;

/**
 * This class contains the instructions for creating a Building object. Each Building represents
 * a unique building structure on USC's University Park Campus. Building objects can facilitate
 * User activity by removing and admitting students as well as updating internal capacity.
 */
public class Building implements Serializable {

    private String abbreviation;
    private String name;
    private int capacity;
    private int currentCount;

    // Have to use an image builder, using String for now
    private String QRCode;
    private int percentage;

   private List<User> students = new ArrayList<>();

    /**
     * Accesses Building object via a default constructor.
     */
    public Building() { }

    /**
     * Accesses private data members of Building for building creation via constructor.
     * @param name
     * @param abbreviation
     * @param capacity
     * @param QRCode
     */
    public Building(String name, String abbreviation, int capacity, int currentCount, String QRCode) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.QRCode = QRCode;
        this.percentage = 0;
    }

    /**
     * @return the abbreviation of the name of the current building.
     */
    public String getAbbreviation() { return this.abbreviation; }

    public void setAbbreviation(String a) { this.abbreviation = a; }

    /**
     * @return the name of the current building.
     */
    public String getName() { return this.name; }

    /**
     * Sets the name of the building
     * @param name
     */
    public void setName(String name) { this.name = name; }


    /**
     * @return the building's capacity.
     */
    public int getCapacity() { return this.capacity; }

//    public int getCurrentCount() {
//
//        if(students == null){
//            return 0;
//        }
//
//        if (!students.isEmpty()) {
//            return 0;
//        }
//        return students.size();
//    }

    /**
     * Updates the building capacity.
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
        //referenceBuildings.child(abbreviation).child("capacity").setValue(capacity);
    }


    /**
     * @return the building's QRCode.
     */
    public String getQRCode() { return this.QRCode; }


    public void setQRCode(String code) { this.QRCode = code; }

    /**
     * @return the building's list of admitted students.
     */
    public List<User> getCurrentStudents() {
        if (students == null) return null;
        System.out.println("HERE: " + this.students.size());
        return this.students;
    }

    public void setStudents(List<User> students) { this.students = students; }


    /**
     * @return the number of students currently in the building.
     * CURRENTLY BREAKS CODE DUE TO ACCESSING EMPTY DATA STRUCTURE
     */
    public int getCurrentCount() {
        return this.currentCount;


    }


    /**

     * @return percentage of building filled up.
     */
    public int getPercent() {
        double cur = (double) this.getCurrentCount();
        double cap = (double) this.capacity;
        double perc = (cur/cap)*100;
        this.percentage = (int) perc;
        return this.percentage;
    }


    public void setCapacity(int capacity, String abb) {
        System.out.println("SETTING CAPACITY FOR: " + abb + " TO " + capacity);
        this.capacity = capacity;
        System.out.println(abb);
        referenceBuildings.child(abb).child("capacity").setValue(capacity);

    }

    /**
     *  checks if a student is in a building
     *  */
    public Boolean isInBuilding(User user) {
        return students.contains(user);
    }

    /**
     * @param user
     * @return true if the student has been successfully removed from the building.
     */
    public Boolean removeStudent(User user) {
        students.remove(user);
        referenceBuildings.child(abbreviation).child("currentStudents").child(user.getId()).removeValue();
        return true;
    }

    /**
     * @param user
     * @return true if the student has been successfully added into the building.
     */
    public Boolean addStudent(User user) {

        students.add(user);
        referenceBuildings.child(abbreviation).child("currentStudents").child(user.getId()).setValue(user);
        return true;
    }
}
