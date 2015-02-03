package com.services;

public class Categori {
    String type;

    Categori(String type, int id) {
        this.type = type;
        this.id = id;
    }

    int id;
    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }



}