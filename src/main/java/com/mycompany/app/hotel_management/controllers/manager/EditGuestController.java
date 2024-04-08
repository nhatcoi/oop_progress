package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.repositories.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditGuestController {
    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TableView<Guest> tableViewUser;

    @FXML
    private AnchorPane guests;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfName;
    private Connection connect;
    private final ObservableList<Guest> guestsList = FXCollections.observableArrayList();
    public void initialize() throws SQLException {
        // code display username of staff from db


        // add display data to the table
        tableViewUser.setItems(guestsList);


        // Set up the columns in the table
        tableViewUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
                if(guest != null) {
                    tfName.setText(guest.getName());
                    tfPhoneNumber.setText(guest.getPhone());
                    tfAddress.setText(guest.getAddress());
                }
            }
        });

        fetchData();
    }

    public void fetchData(){
        try {
            connect = database.connectDb();
            ResultSet rs = connect.createStatement().executeQuery("SELECT guests.*,users.username FROM guests LEFT JOIN users ON users.id = guests.user_id");
            while (rs.next()) {
                guestsList.add(new Guest(rs.getInt("id"),rs.getString("username"), rs.getString("name"), rs.getString("phone"), rs.getString("address"),rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void RemoveData(ActionEvent actionEvent) {
        Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
        if(guest != null) {
            try {
                connect.createStatement().executeUpdate("DELETE FROM guests WHERE id = " + guest.getId());
                guestsList.remove(guest);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
