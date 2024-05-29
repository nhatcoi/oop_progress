package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.service.Impl.GuestServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class EditGuestController {

    @FXML
    private TableView<Guest> tableViewUser;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;

    private final ObservableList<Guest> guestsList = FXCollections.observableArrayList();
    private final GuestServiceImpl guestService = new GuestServiceImpl();

    @FXML
    public void initialize() throws SQLException {
        tableViewUser.setItems(guestsList);

        tableViewUser.setOnMouseClicked(event -> populateFields());

        guestService.getData(guestsList);
    }

    private void populateFields() {
        Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
        if (guest != null) {
            tfName.setText(guest.getName());
            tfPhoneNumber.setText(guest.getPhone());
            tfAddress.setText(guest.getAddress());
            tfEmail.setText(guest.getEmail());
        }
    }

    @FXML
    void removeData() {
        Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
        if (guest != null) {
            guestService.deleteData(guestsList, guest);
        }
    }

    @FXML
    void changeData() {
        Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
        if (guest != null) {
            guest.setName(tfName.getText());
            guest.setPhone(tfPhoneNumber.getText());
            guest.setAddress(tfAddress.getText());
            guest.setEmail(tfEmail.getText());
            guestService.updateData(guestsList, guest);
        }
    }
}
