package com.example.androidcontactsapp;


public class ContactModel {
    private long id;
    private String name;

    // Constructor with ID and name
    public ContactModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for ID
    public long getId() {
        return id;
    }

    // Setter for ID
    public void setId(long id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
}