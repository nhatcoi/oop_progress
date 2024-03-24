package com.mycompany.app.week5to6.controllers;

import com.mycompany.app.App;
import com.mycompany.app.week5to6.models.Room;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeController {
    @FXML
    public Button createBtn;
    @FXML
    public TableView<Room> tableRoom;

    public void initialize() {
        ObservableList<Room> rooms = Room.getRooms();
        tableRoom.setItems(rooms);
    }

    @FXML
    public void Create(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(App.class.getResource("create-view.fxml"))));

        Scene scene = new Scene(root, 640, 320);
        Stage stage = new Stage();
        stage.setTitle("Create Room");
        stage.setScene(scene);
        stage.show();
    }
}
