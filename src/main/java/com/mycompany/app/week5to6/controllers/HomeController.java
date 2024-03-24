package com.mycompany.app.week5to6.controllers;

import com.mycompany.app.App;
import com.mycompany.app.week5to6.models.Room;
import com.mycompany.app.week5to6.util.Dialogs;
import com.mycompany.app.week5to6.util.Utility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeController {
    @FXML
    public Button createBtn;
    @FXML
    public TableView<Room> tableRoom;

    public static ObservableList<Room> data = Room.getRooms();
    public Button btnDelete;

    public void initialize() {
        tableRoom.setItems(data);
        tableRoom.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Room, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Sử dụng PropertyValueFactory để lấy dữ liệu từ thuộc tính "id" của Room

        TableColumn<Room, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type")); // Sử dụng PropertyValueFactory để lấy dữ liệu từ thuộc tính "type" của Room

        TableColumn<Room, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status")); // Sử dụng PropertyValueFactory để lấy dữ liệu từ thuộc tính "status" của Room

        TableColumn<Room, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price")); // Sử dụng PropertyValueFactory để lấy dữ liệu từ thuộc tính "price" của Room

        // Thêm các cột vào TableView
        tableRoom.getColumns().addAll(idColumn, typeColumn, statusColumn, priceColumn);
    }

    public void refreshTable(){
        tableRoom.refresh();

        Utility.writeJSONFile(data,"data.json");
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

    public void deleteData(ActionEvent actionEvent) {
        Room selectedRoom = tableRoom.getSelectionModel().getSelectedItem();
        data.remove(selectedRoom);
        refreshTable();
    }
}
