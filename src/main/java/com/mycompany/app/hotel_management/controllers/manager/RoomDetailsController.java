package com.mycompany.app.hotel_management.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private ImageView imageRoom;

    @FXML
    private AnchorPane showRoomDetails;

    int currentImageIndex = 0;
    public void initialize() throws SQLException {
        switchImage(0);
    }

    @FXML
    void switchRight() {
        switchImage(1);
    }

    @FXML
    void switchLeft() {
        switchImage(-1);
    }

    void switchImage(int i) {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex + i + images.size()) % images.size();
            imageRoom.setImage(images.get(currentImageIndex));
            lbName.setText(roomList.get(currentImageIndex).getName());
            lbType.setText(roomList.get(currentImageIndex).getType());
            lbPrice.setText(String.valueOf(roomList.get(currentImageIndex).getPrice()) + "$/day ");
            lbStatus.setText(roomList.get(currentImageIndex).getStatus());
        }
    }

    public void switchRoom(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.RIGHT) {
            switchRight();
        } else if(keyEvent.getCode() == KeyCode.LEFT) {
            switchLeft();
        }
    }

}