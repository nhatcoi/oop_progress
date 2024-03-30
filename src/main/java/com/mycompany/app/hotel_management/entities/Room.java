package com.mycompany.app.hotel_management.entities;

public class Room {
    private int id;
    private String type;
    private String status;
    private double price;

    // Constructors
    public Room() {
    }

    public Room(String type, String status, double price) {
        this.type = type;
        this.status = status;
        this.price = price;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
