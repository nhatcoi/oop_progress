package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.security.Provider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OverviewController extends Database {
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
    @FXML
    private Button btnChangeImage;


    public int currentImageIndex = 0;
    Connection connect;

    RoomServiceImpl sv = new RoomServiceImpl();
    public final ObservableList<Image> images = FXCollections.observableArrayList();
    public final ObservableList<Room> roomList = FXCollections.observableArrayList();
    public void initialize() throws SQLException {
        long startTime = System.nanoTime();

        connect = Database.connectDb();
        sv.getAllRoom(connect, roomList, "rooms");
        sv.getImage(connect, roomList, images);
        setTag();

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Initialization time: " + elapsedTime + " nanoseconds");
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
        // Tổng tổng tiền của các phòng đã cho thuê
        lbTotalIncome.setText(String.valueOf(roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.OCCUPIED.getText())).mapToDouble(Room::getPrice).sum()));
    }
}