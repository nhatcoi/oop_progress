package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.var;

import java.sql.Connection;
import java.sql.SQLException;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.roomBooking;

public class FindController extends HomeController {
    @FXML
    private TextField tfSearch;
    @FXML
    ComboBox<String>  cbType;
    @FXML
    ComboBox<String> cbPrice;
    @FXML
    private TableView<Room> tableRoom;
    Connection connect;
    @FXML
    private ImageView imageRoom;
    // private final ObservableList<Room> rooms = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        long startTime = System.nanoTime();

        for (RoomType roomType : RoomType.values()) {
            cbType.getItems().add(roomType.getText());
        }
        cbType.getItems().add("All");
        cbPrice.getItems().addAll("low -> high", "high -> low", "Default");

        tableRoom.setItems(rooms);
        tableRoom.setOnMouseClicked(event -> {
            tableRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Room room = tableRoom.getSelectionModel().getSelectedItem();
                if(room != null) {
                    int roomIndex = rooms.indexOf(room);
                    if(roomIndex != -1 && roomIndex < images.size()) {
                        imageRoom.setImage(images.get(roomIndex));
                    }
                }
            });
        });

        ToolFXML.test("Find : ", startTime);
    }

    @FXML
    void typeFilter() {
        var type = cbType.getValue();
        if (type !=  null) {
            if(type.equals("All")) {
                tableRoom.setItems(rooms);
                return;
            }
            ObservableList<Room> filterRooms = FXCollections.observableArrayList();
            for (Room room : rooms) {
                if (room.getType().equals(type)) {
                    filterRooms.add(room);
                }
            }
            tableRoom.setItems(filterRooms);
        }
    }
    @FXML
    void priceFilter() {
        var price = cbPrice.getValue();
        if (price != null) {
            if (price.equals("low -> high")) {
                rooms.sort((o1, o2) -> (int) (o1.getPrice() - o2.getPrice()));
            } else if (price.equals("high -> low")) {
                rooms.sort((o1, o2) -> (int) (o2.getPrice() - o1.getPrice()));
            } else {
                rooms.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
            }
            tableRoom.setItems(rooms);
        }
    }

    @FXML
    public void findRoom() throws SQLException {
        sv.getAllRoom(connect, rooms, "rooms");
        String search = tfSearch.getText();
        if (search.isEmpty()) {
            return;
        }
        ObservableList<Room> searchRooms = FXCollections.observableArrayList();
        for (Room room : rooms) {
            if (room.getName().contains(search)) {
                searchRooms.add(room);
            }
        }
        tableRoom.setItems(searchRooms);
    }

    @FXML
    void addToCart() {
        Room room = tableRoom.getSelectionModel().getSelectedItem();
        if(room == null) {
            Dialog.showError("No room selected", null, "Please select a room to add to cart");
            return;
        }
        if(roomBooking.contains(room)) {
            Dialog.showError("Room is already in cart", null, "Room " + room.getName() + " is already in cart, please open cart to check in room");
            return;
        }
        roomBooking.add(room);
        Dialog.showInformation("Add to Cart", null, "Add room " + room.getName() + " to payment Successfully \nCarry out payment to finish booking");
    }

    @FXML
    void favoriteRoom() {

    }

}