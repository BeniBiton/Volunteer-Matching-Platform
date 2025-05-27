package com.example.models;

public class Volunteer extends User{
    private double hoursVolunteered;
    Volunteer(String id, String name, String email) {
        super(id, name, email);
        this.hoursVolunteered = 0.0;
    }

    @Override
    public String toString(){
        return "Volunteer Info:" + "\n" +
                "\t" + "Id: " + this.id + "\n" +
                "\t" + "Name: " + this.name + "\n" +
                "\t" + "Email: " + this.email + "\n" +
                "\t" + "Hours volunteered: " + this.hoursVolunteered + "\n";
    }
}
