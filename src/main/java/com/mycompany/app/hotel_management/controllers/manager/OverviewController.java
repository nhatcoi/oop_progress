package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.Service.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

public class OverviewController  {
    @FXML
    private Label lbAvailable;

    @FXML
    private Label lbTotalIncome;

    @FXML
    private Label lbRented;

    @FXML
    private ImageView image1;


    public int currentImageIndex = 0;
    Connection connect;

    RoomServiceImpl sv = new RoomServiceImpl();
    public static ObservableList<Image> images = FXCollections.observableArrayList();
    public static final ObservableList<Room> roomList = FXCollections.observableArrayList();
    public void initialize() throws SQLException, ParseException {
        roomList.clear();
        images.clear();
        connect = Database.connectDb();
        sv.getAllRoom(connect, roomList, "rooms");
        images = sv.getImage(connect, roomList, images);
        setTag();
    }

    @FXML
    void changeImage(ActionEvent actionEvent) throws SQLException {
        if (!images.isEmpty()) {
            image1.setImage(images.get(currentImageIndex));
            currentImageIndex = (currentImageIndex + 1) % images.size();
        }
    }

    @FXML
    void refresh() throws SQLException {
        sv.getAllRoom(connect, roomList, "rooms");
        setTag();
    }

    void setTag() {
        lbAvailable.setText(String.valueOf(roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.AVAILABLE.getText())).count()));
        lbRented.setText(String.valueOf(roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.OCCUPIED.getText())).count()));
        // Tổng tổng tiền của các phòng đã cho thuê hom nay
        lbTotalIncome.setText(String.valueOf(roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.OCCUPIED.getText())).mapToDouble(Room::getPrice).sum()));
    }
}