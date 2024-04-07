package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.repositories.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OverviewController extends EditRoomController {
    @FXML
    private AnchorPane overview;
    @FXML
    private Label lbAvailable;

    @FXML
    private Label lbTotalIncome;

    @FXML
    private Label lbRented;

    @FXML
    private ImageView image1;

    @FXML
    private AnchorPane slidePane;


    EditRoomController ed;
    public void initialize() throws SQLException {
        // TODO
        super.fetchDataFromDatabase();

        lbAvailable.setText(String.valueOf(roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.AVAILABLE.getText())).count()));

        lbRented.setText(String.valueOf(roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.OCCUPIED.getText())).count()));

        // Tổng tổng tiền của các phòng đã cho thuê
        lbTotalIncome.setText(String.valueOf(roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.OCCUPIED.getText())).mapToDouble(Room::getPrice).sum()));

        // TODO Lưu total income vào db

    }
}
