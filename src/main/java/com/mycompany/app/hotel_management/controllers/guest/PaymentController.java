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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class PaymentController {

    @FXML
    private TableView<?> tableViewPay;
    @FXML
    private TableView<Room> tableViewBooking;

    @FXML
    private Label lbTotal;
    @FXML
    private ComboBox<String> cbPaymentMethod;

    public static ObservableList<Room> roomBooking = FXCollections.observableArrayList();

    private final ObservableList<Payment> payments = FXCollections.observableArrayList();

    public void initialize() {
        for(PaymentMethod paymentMethod : PaymentMethod.values()) {
            cbPaymentMethod.getItems().add(paymentMethod.getText());
        }

        if(!cbPaymentMethod.getItems().isEmpty()) {
            cbPaymentMethod.getSelectionModel().select(0);
        }

        tableViewBooking.setItems(roomBooking);

        tableViewBooking.setOnMouseClicked(e -> {
            Room room = tableViewBooking.getSelectionModel().getSelectedItem();
            if(room != null) {
                lbTotal.setText(String.valueOf(room.getPrice()));
            }
        });
    }

    // fetch Data from roomlick booking


    @FXML
    void payment() {
        // conduct payment

    }

    @FXML
    void delete() {

    }

}