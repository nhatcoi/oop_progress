package com.mycompany.app.hotel_management.controllers.guest;


import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.repositories.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import lombok.var;

import java.sql.Connection;

public class FindController {

    @FXML
    private TableView<Room> tableRoom;
    Connection connect;
    @FXML
    private ImageView imageRoom;
    private final ObservableList<Room> rooms = FXCollections.observableArrayList();

    public void initialize() {
        if (tableRoom != null) {
            tableRoom.setItems(rooms);

            String sql = "SELECT * FROM rooms";
            connect = Database.connectDb();

            try {
                var statement = connect.createStatement();
                var result = statement.executeQuery(sql);
                while (result.next()) {
                    var room = new Room(result.getString("name"), result.getString("type"), result.getString("status"), result.getDouble("price"));
                    rooms.add(room);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void typeRoom(ActionEvent event) {

    }

    @FXML
    void single(ActionEvent event) {

    }

    @FXML
    void doubleRoom(ActionEvent event) {

    }

    @FXML
    void tripleRoom(ActionEvent event) {

    }

    @FXML
    void kingRoom(ActionEvent event) {

    }

    @FXML
    void booking(ActionEvent event) {

    }

    @FXML
    void addtoCart(ActionEvent event) {

    }

    @FXML
    void favoriteRoom(ActionEvent event) {

    }

    public void otherRoom(ActionEvent actionEvent) {
    }
}