package com.mycompany.app.hotel_management.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Room {
    private int id;
    private String name;
    private String type;
    private String status;
    private double price;

    public Room(String name, String type, String status, double price) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }

}
