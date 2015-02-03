package com.services;

public class Categori {
    String type;
    int id;

    Categori(String type, int id) {
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