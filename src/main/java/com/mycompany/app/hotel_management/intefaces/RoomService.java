package com.mycompany.app.hotel_management.intefaces;

import com.mycompany.app.hotel_management.entities.Room;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.util.List;

interface RoomService {
    ObservableList<Room> getAllRoom() throws SQLException;
    ObservableList<Image> getImage() throws SQLException;
}