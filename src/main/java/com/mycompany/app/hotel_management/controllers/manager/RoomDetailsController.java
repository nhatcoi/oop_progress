package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

import static com.mycompany.app.hotel_management.controllers.guest.HomeController.imageCache;

public class RoomDetailsController extends OverviewController {

    @FXML
    private Label lbName, lbType, lbPrice, lbStatus;
    @FXML
    private ImageView imageRoom;
    @FXML
    private AnchorPane showRoomDetails;

    private int currentIndex = 0;
    private final RoomServiceImpl roomService = new RoomServiceImpl();

    @FXML
    public void initialize() throws SQLException {
        switchInfoRoom(0);
    }

    @FXML
    private void switchRight() {
        switchInfoRoom(1);
    }

    @FXML
    private void switchLeft() {
        switchInfoRoom(-1);
    }

    private void switchInfoRoom(int offset) {
        currentIndex = (currentIndex + offset + roomList.size()) % roomList.size();
        Room currentRoom = roomList.get(currentIndex);
        updateRoomDetails(currentRoom);
    }

    private void updateRoomDetails(Room room) {
        lbName.setText(room.getName());
        lbType.setText(room.getType());
        lbPrice.setText(String.valueOf(room.getPrice()) + "$/day");
        lbStatus.setText(room.getStatus());

        Image img = imageCache.getOrDefault(currentIndex, null);
        if (img == null) {
            img = roomService.fetchImageRoom(room);
            imageCache.put(currentIndex, img);
        }
        imageRoom.setImage(img);
    }

    @FXML
    private void switchRoom(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            switchRight();
        } else if (keyEvent.getCode() == KeyCode.LEFT) {
            switchLeft();
        }
    }
}
