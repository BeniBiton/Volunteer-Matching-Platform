package com.example.volunteer_matching_platform.Enums;

public enum Skill {
    SKILL_TEACHING("Teaching"),
    SKILL_COOKING("Cooking"),
    SKILL_DRIVING("Driving"),
    SKILL_FIRST_AID("First Aid"),
    SKILL_LANGUAGES("Languages");

    private final String label;

    Skill(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
