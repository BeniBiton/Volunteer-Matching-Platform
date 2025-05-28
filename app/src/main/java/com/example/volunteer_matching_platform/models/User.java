package com.example.volunteer_matching_platform.models;

public abstract class User {
    protected String id;
    protected String name;
    protected String email;

    protected User(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean equals(User otherUser){
        if (otherUser == null) return false;
        return this.id.equals(otherUser.id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
