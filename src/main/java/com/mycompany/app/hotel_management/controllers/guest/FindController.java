package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.var;

import java.sql.Connection;
import java.sql.SQLException;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.roomBooking;

public class FindController extends HomeController {

    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<String> cbPrice;
    @FXML
    private TableView<Room> tableRoom;
    @FXML
    private ImageView imageRoom;
    private Connection connect;

    RoomServiceImpl sv = new RoomServiceImpl();

    @Override
    public void initialize() throws SQLException {
        // Populate ComboBoxes
        for (RoomType roomType : RoomType.values()) {
            cbType.getItems().add(roomType.getText());
        }
        cbType.getItems().add("All");
        cbPrice.getItems().addAll("low -> high", "high -> low", "Default");

        // Set initial items in TableView
        tableRoom.setItems(rooms);
        tableRoom.setOnMouseClicked(event -> updateRoomImage());
    }

    @FXML
    private void typeFilter() {
        String type = cbType.getValue();
        if (type == null) return;

        ObservableList<Room> filterRooms = FXCollections.observableArrayList();
        if (type.equals("All")) {
            filterRooms.setAll(rooms);
        } else {
            for (Room room : rooms) {
                if (room.getType().equalsIgnoreCase(type)) {
                    filterRooms.add(room);
                }
            }
        }
        tableRoom.setItems(filterRooms);
    }

    @FXML
    private void priceFilter() {
        String price = cbPrice.getValue();
        if (price == null) return;

        if (price.equals("low -> high")) {
            rooms.sort((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
        } else if (price.equals("high -> low")) {
            rooms.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        } else {
            rooms.sort((o1, o2) -> Long.compare(o1.getId(), o2.getId()));
        }
        tableRoom.setItems(rooms);
    }

    @FXML
    private void findRoom() {
        var search = tfSearch.getText().trim().toLowerCase();
        if (search.isEmpty()) {
            tableRoom.setItems(rooms);
            return;
        }

        ObservableList<Room> searchRooms = FXCollections.observableArrayList();
        for (Room room : rooms) {
            if (room.getName().toLowerCase().contains(search) || room.getType().toLowerCase().contains(search)) {
                searchRooms.add(room);
            }
        }
        tableRoom.setItems(searchRooms);
    }

    @FXML
    private void addToCart() {
        Room room = tableRoom.getSelectionModel().getSelectedItem();
        if (room == null) {
            Dialog.showError("No room selected", null, "Please select a room to add to cart");
            return;
        }
        if (roomBooking.contains(room)) {
            Dialog.showError("Room is already in cart", null, "Room " + room.getName() + " is already in cart, please open cart to check in room");
            return;
        }
        roomBooking.add(room);
        Dialog.showInformation("Add to Cart", null, "Add room " + room.getName() + " to payment Successfully \nCarry out payment to finish booking");
    }

    @FXML
    private void favoriteRoom() {
        // Implement favorite room functionality here
    }

    private void updateRoomImage() {
        Room room = tableRoom.getSelectionModel().getSelectedItem();
        if (room != null) {
            int roomIndex = rooms.indexOf(room);
            if (roomIndex != -1 && roomIndex < rooms.size()) {
                Image img = imageCache.getOrDefault(roomIndex, null);
                if (img == null) {
                    img = sv.fetchImageRoom(rooms.get(roomIndex));
                    imageCache.put(roomIndex, img);
                }
                imageRoom.setImage(img);
                System.out.println(rooms.get(roomIndex));
                System.out.println("Image " + roomIndex + " : " + img);
            }
        }
    }

}