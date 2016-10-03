package com.model;

public class Category {
    String type;
    int id;

    public Category(String type, int id) {
        this.type = type;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }


}