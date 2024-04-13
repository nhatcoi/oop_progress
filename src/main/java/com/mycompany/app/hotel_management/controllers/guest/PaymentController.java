package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.PaymentMethod;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.mycompany.app.hotel_management.controllers.ManagerController.user;

public class PaymentController {

    @FXML
    private TableView<?> tableViewPay;
    @FXML
    private TableView<Room> tableViewBooking;

    @FXML
    private Label lbTotal;
    @FXML
    private ComboBox<String> cbPaymentMethod;
    @FXML
    private DatePicker dpCheckIn;
    @FXML
    private DatePicker dpCheckOut;

    public static ObservableList<Room> roomBooking = FXCollections.observableArrayList();
    private final ObservableList<Payment> payments = FXCollections.observableArrayList();


    ObservableList<Room> selectedRooms;
    int idxRoomSelect = 0;

    public void initialize() {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            cbPaymentMethod.getItems().add(paymentMethod.getText());
        }

        if (!cbPaymentMethod.getItems().isEmpty()) {
            cbPaymentMethod.getSelectionModel().select(0);
        }

        // TODO fetch db

        tableViewBooking.setItems(roomBooking);
        tableViewBooking.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableViewBooking.setOnMouseClicked(e -> {
            selectedRooms = tableViewBooking.getSelectionModel().getSelectedItems();

            double total = 0;
            int i = 0;
            for (Room room : selectedRooms) {
                idxRoomSelect = i++;
                total += room.getPrice();
                lbTotal.setText(String.valueOf(total));
            }
        });

        // set time present
        dpCheckIn.setValue(LocalDate.now());
        dpCheckOut.setValue(LocalDate.now());

        // TODO fetch reservation and set into table

    }

    @FXML
    void payment() {
        // conduct payment
        Reservation res = new Reservation();
        res.setUser_id(user.getId());
        for (Room room : selectedRooms) {
            res.setRoom_id(room.getId());
        }
        res.setCheckInDate(convertToDate(dpCheckIn.getValue()));
        checkOut();
        res.setCheckoutDate(convertToDate(dpCheckOut.getValue()));

        for (Room room : selectedRooms) {
            room.setStatus(RoomStatus.OCCUPIED.getText());
        }
        Dialog.showInformation("Reservation successful", null, "You have successfully booked your room");
        roomBooking.remove(idxRoomSelect);
        initialize();

        // TODO insert into db

        // Tester
        System.out.println("user id : " + res.getUser_id());
        System.out.println("room id : " + res.getRoom_id());
        System.out.println(res.getCheckInDate());
        System.out.println(res.getCheckoutDate());
    }

    public void checkIn() {
        checkDate();
    }

    public void checkOut() {
        checkDate();
    }

    private Date convertToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    private void checkDate() {
        if( dpCheckOut.getValue() == null || dpCheckIn.getValue().isAfter(dpCheckOut.getValue()) || dpCheckIn.getValue().isBefore(LocalDate.now()) ) {
            Dialog.showError("Date", null, "please choose check in or checkout out suitable");
            dpCheckIn.setValue(LocalDate.now());
            dpCheckOut.setValue(LocalDate.now());
            return;
        }
        long daysBetween = ChronoUnit.DAYS.between(dpCheckIn.getValue(), dpCheckOut.getValue());

        if(daysBetween == 0) {
            daysBetween = 1;
        }
        double price = 0;
        for (Room room : selectedRooms) {
            price += room.getPrice();
        }
        double total =  price * daysBetween;
        lbTotal.setText(String.valueOf(total));

        System.out.println(daysBetween);
    }

    @FXML
    void delete() {

    }


}