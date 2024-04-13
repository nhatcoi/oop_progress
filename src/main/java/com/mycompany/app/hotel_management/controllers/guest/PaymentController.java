package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.PaymentMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class PaymentController {

    @FXML
    private TableView<?> tableViewPay;

    @FXML
    private Label lbTotal;
    @FXML
    private ComboBox<String> cbPaymentMethod;

    private static final ObservableList<Room> roomBooking = FXCollections.observableArrayList();

    private final ObservableList<Payment> payments = FXCollections.observableArrayList();

    public void initialize() {
        for(PaymentMethod paymentMethod : PaymentMethod.values()) {
            cbPaymentMethod.getItems().add(paymentMethod.getText());
        }

    }

    // fetch Data from room click booking


    @FXML
    void payment() {

    }

    @FXML
    void delete() {

    }

}