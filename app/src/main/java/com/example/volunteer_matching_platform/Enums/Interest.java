package com.example.volunteer_matching_platform.Enums;

public enum Interest {
    ENVIRONMENT("Environment"),
    HEALTH("Health"),
    EDUCATION("Education"),
    ANIMALS("Animals"),
    ELDERLY_CARE("Elderly Care");

    private final String displayName;

    Interest(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
