package com.mycompany.app.hotel_management.entities;

public class Room {
    private int id;
    private String name;
    private String type;
    private String status;
    private double price;

    // Constructors, getters and setters for other fields...

    // Constructor with name parameter
    public Room(int id, String name, String type, String status, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }
}
