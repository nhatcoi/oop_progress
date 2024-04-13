package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.controllers.manager.OverviewController;
import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.PaymentMethod;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.var;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.mycompany.app.hotel_management.controllers.ManagerController.user;

public class PaymentController extends HomeController {

    @FXML
    private TableView<Reservation> tableViewReservation;
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

    Connection connect;
//    ObservableList<Room> rooms = FXCollections.observableArrayList();

    public static ObservableList<Room> roomBooking = FXCollections.observableArrayList();
    private final ObservableList<Payment> payments = FXCollections.observableArrayList();
    private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();


    ObservableList<Room> selectedRooms;
    int idxRoomSelect = 0;

    RoomServiceImpl sv = new RoomServiceImpl();

    public void initialize() throws SQLException {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            cbPaymentMethod.getItems().add(paymentMethod.getText());
        }
        if (!cbPaymentMethod.getItems().isEmpty()) {
            cbPaymentMethod.getSelectionModel().select(0);
        }

        // set time present
        dpCheckIn.setValue(LocalDate.now());
        dpCheckOut.setValue(LocalDate.now());

        // TODO fetch db cart
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


        // fetch reservation and set into table
        fetchReservation();
        tableViewReservation.setItems(reservations);
        tableViewReservation.setOnMouseClicked(e -> {
            Reservation reservation = tableViewReservation.getSelectionModel().getSelectedItem();
            if(reservation == null) {
                return;
            }
            // TODO cancel reservation
        });

    }

    @FXML
    void payment() throws SQLException {
        // conduct payment
        Reservation res = new Reservation();
        res.setUser_id(user.getId());
        for (Room room : selectedRooms) {
            res.setRoom_id(room.getId());
        }

        checkIn();
        res.setCheckInDate(convertToDate(dpCheckIn.getValue()));
        checkOut();
        res.setCheckoutDate(convertToDate(dpCheckOut.getValue()));

        // set status
        for (Room room : selectedRooms) {
            room.setStatus(RoomStatus.OCCUPIED.getText());
            System.out.println(room.getStatus());
        }
        Dialog.showInformation("Reservation successful", null, "You have successfully booked your room");


        // update status
        updateStatus();

        // TODO conduct payment method

        // insert into db
        reservations.add(res);
        connect = Database.connectDb();
        String sql = "INSERT INTO reservations (user_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?)";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql);
            prepare.setInt(1, res.getUser_id());
            prepare.setInt(2, res.getRoom_id());
            prepare.setDate(3, (java.sql.Date) res.getCheckInDate());
            prepare.setDate(4, (java.sql.Date) res.getCheckoutDate());
            prepare.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // insert into tableview reservation
        tableViewReservation.getItems().clear();
        fetchReservation();

        roomBooking.remove(idxRoomSelect);
    }

    void fetchReservation() throws SQLException {
        connect = Database.connectDb();
        assert connect != null;
        String sql = "SELECT * FROM reservations WHERE user_id = '" + user.getId() + "'";
        ResultSet rs = connect.createStatement().executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            int user_id = rs.getInt("user_id");
            int room_id = rs.getInt("room_id");
            Date check_in_date = rs.getDate("check_in_date");
            Date check_out_date = rs.getDate("check_out_date");

            reservations.add(new Reservation(id, user_id, room_id, check_in_date, check_out_date));
        }
    }

    void updateStatus() throws SQLException {
        connect = Database.connectDb();
        sv.getAllRoom(connect, rooms, "rooms");
        String sql = "UPDATE rooms SET status = ? WHERE id = ?";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql);
            prepare.setInt(1, RoomStatus.OCCUPIED.ordinal());
            prepare.setInt(2, selectedRooms.get(0).getId());
            prepare.executeUpdate();

            sv.getAllRoom(connect, rooms, "rooms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void checkIn() {
        checkDate();
    }

    @FXML
    void checkOut() {
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
        Room room = tableViewBooking.getSelectionModel().getSelectedItem();
        if(room == null) {
            Dialog.showError("Error", null, "Please select a room to delete");
            return;
        }
        roomBooking.remove(room);
    }
}