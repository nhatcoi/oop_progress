package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;


public class DetailController {

    @FXML
    private Label lbNameRoom;

    @FXML
    private Label lbType;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lbPrice;

    public static Room roomDetail = new Room();

    public static List<Room> roomDetailList = new ArrayList<>();

    public void initialize() {
//        System.out.println(roomDetailList.get(0).toString());
    }

    @FXML
    void booking() {

    }



}