package com.example.volunteer_matching_platform.Models;

import android.util.Log;

import com.example.volunteer_matching_platform.Enums.Skill;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Volunteer extends User {

    private int age;
    private int phoneNumber;
    private String profileImageUrl;
    private String city;
    private String region;
    private List<String> availableDays;
    private List<String> availableTimeSlots;
    private Set<Skill> skills;
    private List<String> interests;
    private List<String> applyEventIds;
    private List<String> acceptedEventIds;
    private List<String> completedEventIds;
    private List<String> ratings;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;

    private double hoursVolunteered;
    private Map<String, Object> eventsJoined;

    public Volunteer() {
        super("", "", "");
        this.skills = new HashSet<>();
        this.availableDays = new ArrayList<>();
        this.availableTimeSlots = new ArrayList<>();
        this.interests = new ArrayList<>();
        this.applyEventIds = new ArrayList<>();
        this.acceptedEventIds = new ArrayList<>();
        this.completedEventIds = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.eventsJoined = new HashMap<>();
    }

    public Volunteer(String id, String name, String email) {
        super(id, name, email);
        this.skills = new HashSet<>();
        this.eventsJoined = new HashMap<>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }

    public List<String> getAvailableTimeSlots() {
        return availableTimeSlots;
    }

    public void setAvailableTimeSlots(List<String> availableTimeSlots) {
        this.availableTimeSlots = availableTimeSlots;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getApplyEventIds() {
        return applyEventIds;
    }

    public void setApplyEventIds(List<String> applyEventIds) {
        this.applyEventIds = applyEventIds;
    }

    public List<String> getAcceptedEventIds() {
        return acceptedEventIds;
    }

    public void setAcceptedEventIds(List<String> acceptedEventIds) {
        this.acceptedEventIds = acceptedEventIds;
    }

    public List<String> getCompletedEventIds() {
        return completedEventIds;
    }

    public void setCompletedEventIds(List<String> completedEventIds) {
        this.completedEventIds = completedEventIds;
    }

    public List<String> getRatings() {
        return ratings;
    }

    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getHoursVolunteered() {
        return hoursVolunteered;
    }

    public void setHoursVolunteered(double hoursVolunteered) {
        this.hoursVolunteered = hoursVolunteered;
    }

    public Map<String, Object> getEventsJoined() {
        return eventsJoined;
    }

    public void setEventsJoined(Map<String, Object> eventsJoined) {
        this.eventsJoined = eventsJoined;
    }
    private Set<Skill> convertStringsToSkills(List<String> skillsStrings) {
        Set<Skill> skillSet = new HashSet<>();
        for (String skillStr : skillsStrings) {
            try {
                String enumName = "SKILL_" + skillStr.toUpperCase().replace(" ", "_");
                skillSet.add(Skill.valueOf(enumName));
            } catch (IllegalArgumentException e) {
                Log.e("VolunteerCreationPage", "cannot find this enum skill");
            }
        }
        return skillSet;
    }

    private List<String> skillSetToStringList(Set<Skill> skills) {
        List<String> skillNames = new ArrayList<>();
        for (Skill skill : skills) {
            String name = skill.name().replace("SKILL_", "").replace("_", " ").toLowerCase();
            skillNames.add(Character.toUpperCase(name.charAt(0)) + name.substring(1));
        }
        return skillNames;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("name", this.name);
        map.put("email", this.email);
        map.put("age", this.age);
        map.put("phoneNumber", this.phoneNumber);
        map.put("profileImageUrl", this.profileImageUrl);
        map.put("city", this.city);
        map.put("region", this.region);
        map.put("availableDays", this.availableDays);
        map.put("availableTimeSlots", this.availableTimeSlots);
        map.put("skills", skillSetToStringList(this.skills)); // convert enum to list of strings
        map.put("interests", this.interests);
        map.put("applyEventIds", this.applyEventIds);
        map.put("acceptedEventIds", this.acceptedEventIds);
        map.put("completedEventIds", this.completedEventIds);
        map.put("ratings", this.ratings);
        map.put("createdAt", this.createdAt);
        map.put("updatedAt", this.updatedAt);
        map.put("isActive", this.isActive);
        map.put("hoursVolunteered", this.hoursVolunteered);
        map.put("eventsJoined", this.eventsJoined);
        return map;
    }


    @Override
    public String toString() {
        StringBuilder skillList = new StringBuilder();

        if (skills != null && !skills.isEmpty()) {
            skillList.append("\tSkills:\n");
            for (Skill skill : skills) {
                skillList.append("\t\t").append(skill.toString()).append("\n");
            }
        }

        return "Volunteer Info:\n" +
                "\tId: " + this.id + "\n" +
                "\tName: " + this.name + "\n" +
                "\tEmail: " + this.email + "\n" +
                "\tHours volunteered: " + this.hoursVolunteered + "\n" +
                skillList;
    }
}
