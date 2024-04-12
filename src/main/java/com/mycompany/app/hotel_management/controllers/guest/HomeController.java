package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.controllers.ManagerController;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.Md5;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HomeController {

    @FXML
    private Button search;

    @FXML
    private Label lbPrice2;

    @FXML
    private Label lbPrice1;

    @FXML
    private Label lbName1;

    @FXML
    private Label lbName2;

    @FXML
    private Label lbName3;

    @FXML
    private TextField tfSearch;

    @FXML
    private Label lbPrice3;

    Connection connect;

    private final ObservableList<Room> rooms = FXCollections.observableArrayList();
    private ObservableList<Image> images = FXCollections.observableArrayList();

    RoomServiceImpl sv = new RoomServiceImpl();

    public void initialize() throws SQLException {
        connect = Database.connectDb();
        sv.getAllRoom(connect, rooms, "rooms");
        images = sv.getImage(connect, rooms);

        randomRoom();
    }

    private List<Room> getRandomSublist(List<Room> list, int size) {
        List<Room> sublist = new ArrayList<>(list);
        Collections.shuffle(sublist, new Random());
        return sublist.subList(0, size);
    }

    private void randomRoom() {
        List<Label> names = List.of(lbName1, lbName2, lbName3);
        List<Label> prices = List.of(lbPrice1, lbPrice2, lbPrice3);
        List<Room> selectedRooms = rooms.size() <= 3 ? rooms : getRandomSublist(rooms, 3);

        for (int i = 0; i < selectedRooms.size(); i++) {
            names.get(i).setText(selectedRooms.get(i).getName());
            prices.get(i).setText(String.valueOf(selectedRooms.get(i).getPrice()));
        }
    }

    @FXML
    void otherRoom(ActionEvent actionEvent) {
        randomRoom();
    }
    @FXML
    void searchRoom(ActionEvent actionEvent) throws SQLException {
        String search = tfSearch.getText();
        if (search.isEmpty()) {
            sv.getAllRoom(connect, rooms, "rooms");
        } else {
            try {
                connect = Database.connectDb();
                String sql = "SELECT * FROM rooms WHERE name LIKE '%" + search + "%'";
                assert connect != null;
                ResultSet resultSet = connect.createStatement().executeQuery(sql);
                rooms.clear();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String type = RoomType.values()[resultSet.getInt("type")].getText();
                    String status = RoomStatus.values()[resultSet.getInt("status")].getText();
                    double price = resultSet.getDouble("price");

                    rooms.add(new Room(id, name, type, status, price));
                }
                randomRoom();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void booking1(ActionEvent event) {
    }

    public void booking2(ActionEvent actionEvent) {
    }

    public void booking3(ActionEvent actionEvent) {
    }



}