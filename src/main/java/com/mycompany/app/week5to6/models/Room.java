package com.mycompany.app.week5to6.models;

import com.mycompany.app.week5to6.enums.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Room {
    private int id;
    private String type;
    public String status;
    private String price;

    public Room(int id, String type, String status, String price) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static ObservableList<Room> getRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        rooms.add(new Room(1, "Single", Status.AVAILABLE.getText(), "$50"));
        rooms.add(new Room(2, "Double", Status.BOOKED.getText(), "$80"));
        rooms.add(new Room(3, "Suite", Status.OCCUPIED.getText(), "$120"));
        return rooms;
    }
}
