package com.example.models;

import java.util.HashMap;

public class Organizer extends User{
    private String organizationName;
    private HashMap<String, Event> eventsCommend;

    Organizer(String id, String name, String email, String organizationName) {
        super(id, name, email);
        this.organizationName = organizationName;
        eventsCommend = new HashMap<>();
    }

    @Override
    public String toString(){
        return "Organizer Info:" + "\n" +
                "\t" + "Id: " + this.id + "\n" +
                "\t" + "Name: " + this.name + "\n" +
                "\t" + "Email: " + this.email + "\n" +
                "\t" + "Organization name: " + this.organizationName + "\n\n";
    }
}
