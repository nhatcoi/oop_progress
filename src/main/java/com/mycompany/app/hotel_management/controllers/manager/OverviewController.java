package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import static com.mycompany.app.hotel_management.controllers.AuthController.roomsIni;

public class OverviewController {
    @FXML
    private Label lbAvailable, lbTotalIncome, lbRented;
    @FXML
    private ImageView image1;

    private final RoomServiceImpl roomService = new RoomServiceImpl();

    public static ObservableList<Room> roomList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException, ParseException {
        roomList = roomsIni;
        setTag();
    }

    @FXML
    void changeImage() {
        // This method is currently commented out and not in use
    }

    @FXML
    void refresh() throws SQLException {
        roomService.getAllRoom(roomList, "rooms");
        setTag();
    }

    private void setTag() {
        long availableCount = roomList.stream()
                .filter(room -> RoomStatus.AVAILABLE.getText().equals(room.getStatus()))
                .count();
        long rentedCount = roomList.stream()
                .filter(room -> RoomStatus.OCCUPIED.getText().equals(room.getStatus()))
                .count();
        double totalIncome = roomList.stream()
                .filter(room -> RoomStatus.OCCUPIED.getText().equals(room.getStatus()))
                .mapToDouble(Room::getPrice)
                .sum();

        lbAvailable.setText(String.valueOf(availableCount));
        lbRented.setText(String.valueOf(rentedCount));
        lbTotalIncome.setText(String.valueOf(totalIncome));
    }
}
