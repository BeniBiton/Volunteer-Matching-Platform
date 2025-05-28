package com.example.models;

import java.util.HashMap;
import java.util.HashSet;

public class Volunteer extends User{
    private double hoursVolunteered;
    private HashSet<Skill> skills;
    private HashMap<String, Event> eventsJoined;

    Volunteer(String id, String name, String email) {
        super(id, name, email);
        this.hoursVolunteered = 0.0;
        this.skills = new HashSet<>();
        this.eventsJoined = new HashMap<>();
    }

    @Override
    public String toString(){
        StringBuilder skillList = new StringBuilder();

        if (!this.skills.isEmpty()) {
            skillList.append("\t" + "Skills:" + "\n");
            for (Skill skill : this.skills) {
                skillList.append("\t\t").append(skill.toString()).append("\n");
            }
        }

        skillList.append("\n");

        return "Volunteer Info:" + "\n" +
                "\t" + "Id: " + this.id + "\n" +
                "\t" + "Name: " + this.name + "\n" +
                "\t" + "Email: " + this.email + "\n" +
                "\t" + "Hours volunteered: " + this.hoursVolunteered + "\n" +
                skillList;
    }
}
