package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.Service.GuestServiceImpl;
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
    @FXML
    private TextField tfEmail;

    private Connection connect;
    private final ObservableList<Guest> guestsList = FXCollections.observableArrayList();
    GuestServiceImpl sv = new GuestServiceImpl();
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
                    tfEmail.setText(guest.getEmail());
                }
            }
        });

        sv.getData(connect, guestsList);
    }

    public void removeData(ActionEvent actionEvent) {
        Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
        if(guest != null) {
            sv.deleteData(connect, guestsList, guest);
        }
    }

    public void changeData() {
        Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
        if (guest != null) {
            guest.setName(tfName.getText());
            guest.setPhone(tfPhoneNumber.getText());
            guest.setAddress(tfAddress.getText());
            guest.setEmail(tfEmail.getText());
            sv.updateData(connect, guestsList, guest);
        }
    }
}
