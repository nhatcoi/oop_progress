package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.repositories.database;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditRoomController {
    public Button btnAdd;
    public TextField rnameField;
    public ComboBox<String> cbTypeRoom;
    public TextField rpriceField;
    public TableView<Room> tableView;
    public TextField searchField;
    public Button btnSearch;
    private Connection connect;
    private final ObservableList<Room> roomList = FXCollections.observableArrayList();
    public void initialize() throws SQLException {
        for (RoomType value : RoomType.values()) {
            cbTypeRoom.getItems().add(value.getText());
        }

        if (!cbTypeRoom.getItems().isEmpty()) {
            cbTypeRoom.setValue(cbTypeRoom.getItems().get(0));
        }
        tableView.setItems(roomList);
        fetchDataFromDatabase();
    }
    public void clearInput() {
        rnameField.clear();
        rpriceField.clear();

    }

    public void addData() {
        connect = database.connectDb();
        String name = rnameField.getText();
        int type = cbTypeRoom.getSelectionModel().getSelectedIndex();
        try {
            double price = Double.parseDouble(rpriceField.getText());
            String query = "INSERT INTO rooms (name, type, status, price) VALUES (N'" + name + "', '" + type + "', 0, " + price + ")";
            try {
                assert connect != null;
                connect.createStatement().executeUpdate(query);

                fetchDataFromDatabase();
                clearInput();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (NumberFormatException e) {
            Dialog.showError("Error", "Error", "Price Phải là số!");
            return;
        }
    }

    public void searchData() {
        String search = searchField.getText();
        if (search.isEmpty()) {
            try {
                fetchDataFromDatabase();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                connect = database.connectDb();
                String query = "SELECT * FROM rooms WHERE name LIKE '%" + search + "%'";
                assert connect != null;
                ResultSet resultSet = connect.createStatement().executeQuery(query);
                roomList.clear();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String type = RoomType.values()[resultSet.getInt("type")].getText();
                    String status = RoomStatus.values()[resultSet.getInt("status")].getText();
                    double price = resultSet.getDouble("price");

                    roomList.add(new Room(id, name, type, status, price));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public void deleteData() {
        Room room = tableView.getSelectionModel().getSelectedItem();
        if (room == null) {
            Dialog.showError("Error", "Error", "Chọn phòng cần xóa!");
            return;
        }
        connect = database.connectDb();
        String query = "DELETE FROM rooms WHERE id = " + room.getId();
        try {
            assert connect != null;
            connect.createStatement().executeUpdate(query);
            fetchDataFromDatabase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Dialog.showInformation("Information", "Information", "Xóa" + room.getName() + " thành công!");
    }

    private void fetchDataFromDatabase() throws SQLException {

        connect = database.connectDb();
        String query = "SELECT * FROM rooms";
        assert connect != null;
        ResultSet resultSet = connect.createStatement().executeQuery(query);
        roomList.clear();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String type = RoomType.values()[resultSet.getInt("type")].getText();
            String status = RoomStatus.values()[resultSet.getInt("status")].getText();
            double price = resultSet.getDouble("price");

            roomList.add(new Room(id, name, type, status, price));
        }
    }


}
