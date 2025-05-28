package com.example.models;

import android.location.Location;

import java.util.Date;
import java.util.HashMap;

public class Event {
    private String id;
    private Organizer organizer;
    private String title;
    private String description;
    private Location location;
    private Date date;
    private int needVolunteers;
    private HashMap<String, Volunteer> applicants;
    private HashMap<String, Volunteer> accepted;

    public Event(String id, Organizer organizer, String title, String description, Location location, Date date, int needVolunteers) {
        this.id = id;
        this.organizer = organizer;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.needVolunteers = needVolunteers;
        this.applicants = new HashMap<>();
        this.accepted = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Event:" + "\n" +
                "\t" + "Id: " + this.id + "\n" +
                "\t" + "Organizer name: " + this.organizer.name + "\n" +
                "\t" + "Title: " + this.title + "\n" +
                "\t" + "Description: " + this.description + "\n" +
                "\t" + "Location: " + this.location.toString() + "\n" +
                "\t" + "Date: " + this.date.toString() + "\n" +
                "\t" + "Need Volunteers: " + this.needVolunteers + "\n\n";
    }
}
