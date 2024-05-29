package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardController extends PaymentController {
    @FXML
    private Label lbName;
    @FXML
    private Label lbType;
    @FXML
    private ImageView roomImg;

    ObservableList<Room> roomCards = FXCollections.observableArrayList();
    ObservableList<Image> images = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        System.out.println("initialize card controller");
    }

    public void setData(Room room) throws SQLException {
        System.out.println(room.toString());
        lbName.setText(room.getName());
        lbType.setText(room.getType());
        roomImg.setImage(imageCache.get(rooms.indexOf(room)));
        roomCards.add(room);
    }
}