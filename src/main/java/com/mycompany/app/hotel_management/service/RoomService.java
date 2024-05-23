package com.mycompany.app.hotel_management.service;

import com.mycompany.app.hotel_management.entities.Room;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.SQLException;

public interface RoomService {
    void getAllRoom(ObservableList<Room> roomList, String table) throws SQLException;
    ObservableList<Image> getImage(Connection connect, ObservableList<Room> roomList, ObservableList<Image> images) throws SQLException;
    ObservableList<Image> getImage(Connection connect, ObservableList<Room> roomList) throws SQLException;
    ObservableList<Room> addRoom(Connection connect, ObservableList<Room> roomList, Room room) throws SQLException;
    ObservableList<Room> updateRoom(Connection connect, ObservableList<Room> roomList, Room room) throws SQLException;
    ObservableList<Room> deleteRoom(Connection connect, ObservableList<Room> roomList, Room room) throws SQLException;
    void searchRoom(Connection connect, ObservableList<Room> roomList, String search) throws SQLException;
}