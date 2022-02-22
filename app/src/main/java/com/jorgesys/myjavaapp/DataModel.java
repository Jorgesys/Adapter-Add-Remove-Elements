package com.jorgesys.myjavaapp;

public class DataModel {

    private int id;
    private String name;
    private String version;
    private int image;

    public DataModel(int id, String name, String version, int image) {
        this.name = name;
        this.version = version;
        this.id = id;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getImage() {
        return image;
    }


}