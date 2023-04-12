package com.jeffrey.earthquakeapp;

public class EarthQuakeItem {
    private String name;
    private String description;
    private String date;
    private double magnitude;
    private String location;

    public EarthQuakeItem(String name, String description, String date, double magnitude, String location){
        this.name = name;
        this.description = description;
        this.date = date;
        this.magnitude = magnitude;
        this.location = location;
    }

    public EarthQuakeItem(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "EarthQuakeItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", magnitude=" + magnitude +
                ", location='" + location + '\'' +
                '}';
    }


}

