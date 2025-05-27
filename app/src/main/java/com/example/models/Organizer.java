package com.example.models;

public class Organizer extends User{
    private String organizationName;

    Organizer(String id, String name, String email, String organizationName) {
        super(id, name, email);
        this.organizationName = organizationName;
    }

    @Override
    public String toString(){
        return "Organizer Info:" + "\n" +
                "\t" + "Id: " + this.id + "\n" +
                "\t" + "Name: " + this.name + "\n" +
                "\t" + "Email: " + this.email + "\n" +
                "\t" + "Organization name: " + this.organizationName + "\n";
    }
}
