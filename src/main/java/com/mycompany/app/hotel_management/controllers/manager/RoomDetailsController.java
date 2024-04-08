package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class RoomDetailsController extends OverviewController {

    @FXML
    private Label lbName;

    @FXML
    private Label lbType;

    @FXML
    private Label lbPrice;

    @FXML
    private Label lbStatus;

    @FXML
    private AnchorPane slidePane1;

    @FXML
    private ImageView imageRoom;

    @FXML
    private AnchorPane showRoomDetails;

    RoomServiceImpl roomService = new RoomServiceImpl();


    public int currentImageIndex = 0;
    @FXML
    void switchRight(ActionEvent event) {
        switchImage(1);
    }

    @FXML
    void switchLeft(ActionEvent event) {
        switchImage(-1);
    }

    void switchImage(int i) {
        if (!roomList.isEmpty()) {
            currentImageIndex = (currentImageIndex - i + images.size()) % images.size();

            imageRoom.setImage(images.get(currentImageIndex));

            lbName.setText(roomList.get(currentImageIndex).getName());
            lbType.setText(roomList.get(currentImageIndex).getType());
            lbPrice.setText(String.valueOf(roomList.get(currentImageIndex).getPrice()));
            lbStatus.setText(roomList.get(currentImageIndex).getStatus());

////            currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
//            currentImageIndex--;
//            if(currentImageIndex == 0) {
//                currentImageIndex = images.size()-1;
        } else {
            imageRoom.setImage(new Image("img/goku.png"));

            lbName.setText(roomList.get(currentImageIndex).getName());
            lbType.setText(roomList.get(currentImageIndex).getType());
            lbPrice.setText(String.valueOf(roomList.get(currentImageIndex).getPrice()));
            lbStatus.setText(roomList.get(currentImageIndex).getStatus());
        }
    }
}