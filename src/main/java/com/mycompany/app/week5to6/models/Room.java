package com.mycompany.app.week5to6.models;

import com.mycompany.app.week5to6.enums.Status;
import com.mycompany.app.week5to6.util.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

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
        List<Room> rooms = Utility.readJSONFile("data.json");
        ObservableList<Room> roomData = FXCollections.observableArrayList();
        roomData.addAll(rooms);
        return roomData;
    }
}
