package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.PaymentMethod;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.Service.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.var;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static ObservableList<Room> roomBooking = FXCollections.observableArrayList();
    ObservableList<Room> selectedRooms;
    public static ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    ObservableList<Payment> payments = FXCollections.observableArrayList();
    
    int idxRoomSelect = 0;
    RoomServiceImpl sv = new RoomServiceImpl();
    public static Reservation reservation;

    public void initialize() throws SQLException {
        long startTime = System.nanoTime();
        roomBooking.clear();
        cbPaymentMethod.getItems().addAll(Arrays.stream(PaymentMethod.values())
                .map(PaymentMethod::getText)
                .collect(Collectors.toList()));
        cbPaymentMethod.getSelectionModel().selectFirst();

        dpCheckIn.setValue(LocalDate.now());
        dpCheckOut.setValue(LocalDate.now());

        // cart handing
        tableViewBooking.setItems(roomBooking);
        tableViewBooking.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewBooking.setOnMouseClicked(e -> {
            selectedRooms = tableViewBooking.getSelectionModel().getSelectedItems();
            double total = 0;
            for (Room room : selectedRooms) {
                total += room.getPrice();
            }
            lbTotal.setText(String.valueOf(total));
        });

        // reservations handing
        reservations.clear();
        fetchReservation();
        tableViewReservation.setItems(reservations);
        tableViewReservation.setOnMouseClicked(e -> {
            reservation = tableViewReservation.getSelectionModel().getSelectedItem();
            if (reservation != null && e.getClickCount() == 2) {
                // Open bill payment
                System.out.println("Open bill payment");
                try {
                    ToolFXML.openFXML("views/guest/paymentDetail.fxml", 587, 891);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Update status neu qua han
//        resOutOfDate();

        ToolFXML.test("Payment: ", startTime);
    }

    private void resOutOfDate() throws SQLException {
        for(Reservation resOutOfDate : reservations) {
            if(resOutOfDate.getCheckoutDate().before(new Date())) {
                updateStatus(rooms.get(indexOfRoom(resOutOfDate)), RoomStatus.AVAILABLE.ordinal());
                System.out.println(rooms.get(indexOfRoom(resOutOfDate)).getStatus() + " is out of date : " + indexOfRoom(resOutOfDate));
            }
            System.out.println(rooms.get(indexOfRoom(resOutOfDate)).getStatus() + " is out of date : " + indexOfRoom(resOutOfDate));
        }
    }

    private boolean checkInfo() {
        if(Objects.equals(guest.getName(), "") || Objects.equals(guest.getPhone(), "") || Objects.equals(guest.getAddress(), "") || Objects.equals(guest.getEmail(), "")){
            Dialog.showError("Error", null, "Please fully update your personal information before payment");
            return false;
        }
        if(Objects.equals(guest.getName(), "null") || Objects.equals(guest.getPhone(), "null") || Objects.equals(guest.getAddress(), "null") || Objects.equals(guest.getEmail(), "null")){
            Dialog.showError("Error", null, "Please fully update your personal information before payment");
            return false;
        }
        if(guest.getName() == null || guest.getPhone() == null || guest.getAddress() == null || guest.getEmail() == null){
            Dialog.showError("Error", null, "Please fully update your personal information before payment");
            return false;
        }
        return true;
    }

    @FXML
    void payment() throws SQLException {
        if(!checkInfo()) {
            return;
        }

        // create reservation - gan gia tri
        Reservation res = new Reservation();
        if(!paySelectedRoom(res)) {
            Dialog.showError("Error", null, "Please select a room to book");
            return;
        }

        // xu li trung lich phong
        if(!sameOrder(res)) {
            return;
        }

        // insert reservation into db
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
        tableViewReservation.getItems().clear();
        fetchReservation();
        roomBooking.remove(selectedRooms.get(idxRoomSelect));

        // conduct payment method
        Dialog.showInformation("Payment", null, "Please pay the bill to complete the reservation");
        Payment payment = new Payment();
        reservations.stream()
                .filter(resCheck -> resCheck.equals(res))
                .findFirst()
                .ifPresent(resCheck -> payment.setReservationId(resCheck.getId()));
        payment.setTotalPrice(Double.parseDouble(lbTotal.getText()));
        payment.setPaymentMethod(cbPaymentMethod.getValue());
        payment.setPaymentDate(new Date()); // Gán thời gian hiện tại cho paymentDate

        // xu li sql date
        Date utilDate = payment.getPaymentDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        // insert payment into db
        payments.add(payment);
        connect = Database.connectDb();
        sql = "INSERT INTO payments (reservation_id, total_price, payment_method, payment_date) VALUES (?, ?, ?, ?)";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql);
            prepare.setInt(1, payment.getReservationId());
            prepare.setDouble(2, payment.getTotalPrice());
            prepare.setString(3, payment.getPaymentMethod());
            prepare.setDate(4, sqlDate);
            prepare.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        fetchPayment(res);

        // update status - dung ngay moi update
        LocalDate localDateNow = LocalDate.now(), checkIn = dpCheckIn.getValue(), checkOut = dpCheckOut.getValue();
        if((localDateNow.isAfter(checkIn) && localDateNow.isBefore(checkOut) || localDateNow.isEqual(dpCheckIn.getValue()))){
            updateStatus(rooms.get(indexOfRoom(res)) , RoomStatus.OCCUPIED.ordinal());
        }
        Dialog.showInformation("Reservation successful", null, "You have successfully booked your room");
    }

    private boolean sameOrder(Reservation res) {
        for(Reservation resExist : reservations) {
            if(resExist.getRoom_id() == res.getRoom_id()) {
                if(res.getCheckInDate().equals(resExist.getCheckInDate()) || res.getCheckInDate().equals(resExist.getCheckoutDate()) || (res.getCheckInDate().after(resExist.getCheckInDate()) && res.getCheckInDate().before(resExist.getCheckoutDate())) || (res.getCheckoutDate().after(resExist.getCheckInDate()) && res.getCheckoutDate().before(resExist.getCheckoutDate()))) {
                    Dialog.showError("Error", null, "This Room is already booked from " + resExist.getCheckInDate() + " to " + resExist.getCheckoutDate() + "\n Please choose another room or date");
                    return false;
                }
                if(res.getCheckInDate().before(resExist.getCheckInDate()) && res.getCheckoutDate().after(resExist.getCheckoutDate())) {
                    Dialog.showError("Error", null, "This Room is already booked from " + resExist.getCheckInDate() + " to " + resExist.getCheckoutDate() + "\n Please choose another room or date");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean paySelectedRoom(Reservation res) {
        // conduct payment
        if (selectedRooms == null || selectedRooms.isEmpty()) {
            return false;
        }
        res.setUser_id(user.getId());
        for (Room room : selectedRooms) {
            res.setRoom_id(room.getId());
        }
        checkIn();
        res.setCheckInDate(convertToDate(dpCheckIn.getValue()));
        checkOut();
        res.setCheckoutDate(convertToDate(dpCheckOut.getValue()));
        return true;
    }

    private void fetchReservation() throws SQLException {
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

    private void fetchPayment(Reservation res) throws SQLException {
        connect = Database.connectDb();
        assert connect != null;
        String sql = "SELECT * FROM payments WHERE reservation_id = '" + res.getId() + "'";
        ResultSet rs = connect.createStatement().executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            int reservation_id = rs.getInt("reservation_id");
            double total_price = rs.getDouble("total_price");
            String payment_method = rs.getString("payment_method");
            Date payment_date = rs.getDate("payment_date");

            payments.add(new Payment(id, reservation_id, total_price, payment_method, payment_date));
        }
    }

    private void updateStatus(Room room, int statusRoom) throws SQLException {

        connect = Database.connectDb();
        sv.getAllRoom(connect, rooms, "rooms");
        String sql = "UPDATE rooms SET status = ? WHERE id = ?";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql);
            prepare.setInt(1, statusRoom);
            prepare.setInt(2, room.getId());
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

        double price = 0;
        for (Room room : selectedRooms) {
            price += room.getPrice();
        }
        double total =  price * (daysBetween + 1);
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

    @FXML
    void cancel() throws SQLException {
        // Cancel reservation
        Reservation res = tableViewReservation.getSelectionModel().getSelectedItem();
        if(res == null) {
            Dialog.showError("Error", null, "Please select a reservation to cancel");
            return;
        }
        connect = Database.connectDb();
        // remove payment before reservation
        fetchPayment(res);
        Payment payment = payments.get(payments.size()-1);
        connect = Database.connectDb();
        String sql = "DELETE FROM payments WHERE id = ?";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql);
            prepare.setInt(1, payment.getId());
            prepare.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        String sql2 = "DELETE FROM reservations WHERE id = ?";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql2);
            prepare.setInt(1, res.getId());
            prepare.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // update status
//        int indexOfRoom = -1; // Initialize with a value indicating no match found
//        for (int i = 0; i < rooms.size(); i++) {
//            Room room = rooms.get(i);
//            if (room.getId() == (res.getRoom_id())) {
//                indexOfRoom = i; // Found the matching room, update the index
//                break; // No need to continue searching
//            }
//        }
        // Stream api
//        int indexOfRoom = IntStream.range(0, rooms.size())
//                .filter(i -> rooms.get(i).getId() == res.getRoom_id())
//                .findFirst()
//                .orElse(-1);


        updateStatus(rooms.get(indexOfRoom(res)), RoomStatus.AVAILABLE.ordinal());
        reservations.remove(res);
        Dialog.showInformation("Cancel reservation", null, "You have successfully canceled your reservation");
        tableViewReservation.getItems().clear();
        fetchReservation();
    }

    private int indexOfRoom(Reservation res) {
        return IntStream.range(0, rooms.size())
                .filter(i -> rooms.get(i).getId() == res.getRoom_id())
                .findFirst()
                .orElse(-1);
    }
}