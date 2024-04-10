package com.mycompany.app.hotel_management.intefaces;

import com.mycompany.app.hotel_management.entities.Room;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.SQLException;

interface RoomService {
    void getAllRoom(Connection connect, ObservableList<Room> roomList, String table) throws SQLException;
    ObservableList<Image> getImage(Connection connect, ObservableList<Room> roomList, ObservableList<Image> images) throws SQLException;
}