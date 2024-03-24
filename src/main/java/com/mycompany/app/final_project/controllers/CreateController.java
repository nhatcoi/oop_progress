package com.mycompany.app.final_project.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.app.final_project.enums.Status;
import com.mycompany.app.final_project.models.Room;
import com.mycompany.app.final_project.util.Dialogs;
import com.mycompany.app.final_project.util.Utility;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class CreateController {
    public Button btnCreated;
    public TextField idField;
    public TextField typeField;
    public TextField priceField;
    public ComboBox<String> statusCombo;

    public void initialize() {
        for (Status status : Status.values()) {
            statusCombo.getItems().add(status.getText());
        }
    }

    public void CreateRoom(ActionEvent actionEvent) throws IOException {
        int idRoom = Integer.parseInt(idField.getText());
        String typeRoom = typeField.getText();
        String statusRoom = statusCombo.getValue();
        String priceRoom = priceField.getText();

        List<Room> rooms = Utility.readJSONFile("data.json");

        Room room = new Room(idRoom, typeRoom, statusRoom, priceRoom);

        if (rooms == null) {
            rooms = new ArrayList<>();
        }
        for (Room r : rooms) {
            if (r.getId() == idRoom) {
                Dialogs.showWarning("Warning", "Room Đã Tồn Tại", "Trùng ID gòi má");
                return;
            }
        }
        rooms.add(room);

        Utility.writeJSONFile(rooms, "data.json");

        Dialogs.showInformation("Thành Công", "Create Room", "Create Room Success");
//        Stage stage = (Stage) btnCreated.getScene().getWindow();
//        stage.close();
    }
}
