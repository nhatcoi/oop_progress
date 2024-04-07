package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.repositories.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        fetchDataFromDatabase();


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
    }



    private void fetchDataFromDatabase() throws SQLException {

        connect = database.connectDb();
        String query = "SELECT * FROM guests";
        assert connect != null;
        ResultSet resultSet = connect.createStatement().executeQuery(query);
        guestsList.clear();
        while (resultSet.next()) {
            int id = resultSet.getInt("Id");
            String name = resultSet.getString("name");
            String phone = resultSet.getString("phone");
            String address = resultSet.getString("address");

            guestsList.add(new Guest(id, name, phone, address));
        }
    }
}
