package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.App;
import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.PaymentMethod;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.MailSender;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.var;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mycompany.app.hotel_management.controllers.ManagerController.user;

public class PaymentController extends HomeController {

    @FXML
    private TableView<Reservation> tableViewReservation;
    @FXML
    TableView<Room> tableViewBooking;
    @FXML
    Label lbTotal;
    @FXML
    private ComboBox<String> cbPaymentMethod;
    @FXML
    private DatePicker dpCheckIn;
    @FXML
    private DatePicker dpCheckOut;
    @FXML
    private ComboBox<Integer> cbHourIn;
    @FXML
    private ComboBox<Integer> cbHourOut;

    @FXML
    private ScrollPane cartPane;
    @FXML
    private AnchorPane containPane;

    Connection connect;
    public static ObservableList<Room> roomBooking = FXCollections.observableArrayList();
    //ObservableList<Room> selectedRooms;
    Room room;
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

        cbHourIn.getItems().addAll(IntStream.range(0, 24).boxed().collect(Collectors.toList()));
        cbHourIn.getSelectionModel().select(LocalTime.now().getHour());
        cbHourOut.getItems().addAll(IntStream.range(0, 24).boxed().collect(Collectors.toList()));
        cbHourOut.getSelectionModel().selectLast();

        dpCheckIn.setValue(LocalDate.now());
        dpCheckOut.setValue(LocalDate.now());

        // cart handing
        cartHanding();

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

    private void cartHanding() {
        tableViewBooking.setItems(roomBooking);
        tableViewBooking.setOnMouseClicked(e -> {
            room = tableViewBooking.getSelectionModel().getSelectedItem();
            lbTotal.setText(String.valueOf(room.getPrice()));
        });
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

    @FXML
    void payment() throws Exception {
        if(room.getStatus().equals(RoomStatus.MAINTENANCE.getText())) {
            Dialog.showError("Error", null, "This room is under maintenance, please try again later");
            return;
        }
        if(!roomBooking.contains(room)) {
            Dialog.showError("Error", null, "Please select a room to book");
            return;
        }

        if(!checkInfo()) {
            return;
        }

        // create reservation - gan gia tri
        Reservation res = new Reservation();
        if(!resInfo(res)) {
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
            java.sql.Timestamp checkin = new java.sql.Timestamp(res.getCheckInDate().getTime());
            java.sql.Timestamp checkout = new java.sql.Timestamp(res.getCheckoutDate().getTime());
            prepare.setTimestamp(3, checkin);
            prepare.setTimestamp(4, checkout);
            prepare.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tableViewReservation.getItems().clear();
        fetchReservation();
        roomBooking.remove(room);

        // conduct payment method
        Payment payment = new Payment();
//        reservations.stream()
//                .filter(resCheck -> resCheck.equals(res))
//                .findFirst()
//                .ifPresent(resCheck -> payment.setReservationId(resCheck.getId()));
        payment.setReservationId(reservations.get(reservations.size()-1).getId());
        payment.setTotalPrice(Double.parseDouble(lbTotal.getText()));
        payment.setPaymentMethod(cbPaymentMethod.getValue());
        payment.setPaymentDate(new Date()); // Gán thời gian hiện tại cho paymentDate
        System.out.println(payment.getPaymentDate());

        // insert payment into db
        // xu li sql date
        Date utilDate = payment.getPaymentDate();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());
        System.out.println(timestamp);
        System.out.println(timestamp);
        payments.add(payment);
        connect = Database.connectDb();
        sql = "INSERT INTO payments (reservation_id, total_price, payment_method, payment_date) VALUES (?, ?, ?, ?)";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql);
            prepare.setInt(1, payment.getReservationId());
            prepare.setDouble(2, payment.getTotalPrice());
            prepare.setString(3, payment.getPaymentMethod());
            prepare.setTimestamp(4, timestamp);
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

        // bank lor
        if(cbPaymentMethod.getValue().equals(PaymentMethod.BANK_TRANSFER.getText())) {
            try {
                Dialog.showInformation("Payment", null, "Please pay the bill to complete the reservation");
                reservation = reservations.get(reservations.size()-1);
                ToolFXML.openFXML("views/guest/bank.fxml", 600, 600);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Dialog.showInformation("Reservation successful", null, "You have successfully booked your room");
        }

        MailSender.sendEmail(guest.getEmail(), "Reservation", "You have just booked a room (" + rooms.get(indexOfRoom(res)).getName() + ") from " + dpCheckIn.getValue() + " to " + dpCheckOut.getValue() + " at Viet Nhat Hotel" +
                "\nTotal price: " + lbTotal.getText() + " $" +
                "\nPayment method: " + cbPaymentMethod.getValue() +
                "\nTime Order: " + payment.getPaymentDate() +
                "\nThank you for choosing our service");
    }

    private boolean checkInfo() {
        if (guest == null || guest.getName() == null || guest.getPhone() == null || guest.getAddress() == null || guest.getEmail() == null ||
                guest.getName().isEmpty() || guest.getPhone().isEmpty() || guest.getAddress().isEmpty() || guest.getEmail().isEmpty()) {
            Dialog.showError("Error", null, "Please fully update your personal information before payment");
            return false;
        }
        return true;
    }

    private boolean resInfo(Reservation res) {
        if (room == null) {
            return false;
        }
        res.setUser_id(user.getId());
        res.setRoom_id(room.getId());
        checkIn();
        res.setCheckInDate(setDateTime(convertToDate(dpCheckIn.getValue()), cbHourIn.getValue()));
        checkOut();
        res.setCheckoutDate(setDateTime(convertToDate(dpCheckOut.getValue()), cbHourOut.getValue()));
        return true;
    }

    private java.sql.Timestamp setDateTime(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour); // Set the hour of the day
        return new java.sql.Timestamp(calendar.getTimeInMillis());
    }

    private Date convertToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
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


    private void updateStatus(Room room, int statusRoom) throws SQLException {
        connect = Database.connectDb();
        sv.getAllRoom(rooms, "rooms");
        String sql = "UPDATE rooms SET status = ? WHERE id = ?";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql);
            prepare.setInt(1, statusRoom);
            prepare.setInt(2, room.getId());
            prepare.executeUpdate();

            sv.getAllRoom(rooms, "rooms");
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
    @FXML
    void checkTimeIn() {
        checkDate();
    }
    @FXML
    void checkTimeOut() {
        checkDate();
    }

    private void checkDate() {
        LocalDate checkInDate = dpCheckIn.getValue();
        LocalDate checkOutDate = dpCheckOut.getValue();
        long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        int hoursBetween = cbHourOut.getValue() - cbHourIn.getValue();
        double price = room.getPrice();
        double total = 0;

        // check lệch checkin và checkout thì set lại
        if (checkOutDate == null || checkInDate.isAfter(checkOutDate) || checkInDate.isBefore(LocalDate.now())) {
            Dialog.showError("Date", null, "Please choose suitable check-in and check-out dates");
            dpCheckIn.setValue(LocalDate.now());
            dpCheckOut.setValue(LocalDate.now());
            return;
        }

        // Check checkin trùng ngày hiện tại thì set giờ hiện tại
        if (checkInDate.equals(LocalDate.now())) {
            Integer selectedHour = cbHourIn.getValue();
            Integer currentHour = LocalTime.now().getHour();
            if (selectedHour < currentHour) {
                cbHourIn.setValue(currentHour);
            }
        }
        // Check checkin trùng checkout thì validate giờ
        if (daysBetween == 0 && hoursBetween <= 0) {
            Dialog.showError("Date", null, "Please choose suitable hours");
            cbHourIn.setValue(checkInDate.equals(LocalDate.now()) ? LocalTime.now().getHour() : 0);
            cbHourOut.setValue(23);
            return;
        } else {
            total = price;
        }


        if(daysBetween > 0) {
            daysBetween++;
            if(cbHourOut.getValue() <= cbHourIn.getValue()) {
                daysBetween--;
                total = price * daysBetween;
            }
            total = price * daysBetween;
        }
        lbTotal.setText(String.valueOf(total));
    }

    @FXML
    void delete() {
        cartPane.setVisible(false);

        Room room = tableViewBooking.getSelectionModel().getSelectedItem();
        if(room == null) {
            Dialog.showError("Error", null, "Please select a room to delete");
            return;
        }
        roomBooking.remove(room);
    }

    @FXML
    void cancel() throws Exception {
        // Cancel reservation
        Reservation res = tableViewReservation.getSelectionModel().getSelectedItem();
        if(res == null) {
            Dialog.showError("Error", null, "Please select a reservation to cancel");
            return;
        }
        if(!res.getCheckInDate().after(new Date())) {
            Dialog.showError("Error", null, "You can't cancel a reservation that has already started");
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

        MailSender.sendEmail(guest.getEmail(), "Cancel Reservation", "You have just canceled a room " + rooms.get(indexOfRoom(res)).getName() + ") from " + res.getCheckInDate() + " to " + res.getCheckoutDate() + " at Viet Nhat Hotel" +
                "\nThank you! Hope to see you again");
    }

    private int indexOfRoom(Reservation res) {
        return IntStream.range(0, rooms.size())
                .filter(i -> rooms.get(i).getId() == res.getRoom_id())
                .findFirst()
                .orElse(-1);
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
            java.sql.Timestamp check_in_timestamp = rs.getTimestamp("check_in_date");
            java.sql.Timestamp check_out_timestamp = rs.getTimestamp("check_out_date");

            System.out.println(check_in_timestamp + " " + check_out_timestamp);
            reservations.add(new Reservation(id, user_id, room_id, check_in_timestamp, check_out_timestamp));
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

    @FXML
    private void paymentMethod() {
        if (cbPaymentMethod.getValue().equals(PaymentMethod.BITCOIN.getText()) || cbPaymentMethod.getValue().equals(PaymentMethod.PAYPAL.getText())) {
            Dialog.showWarning("Warning", null, "This payment method is under development");
            cbPaymentMethod.getSelectionModel().selectFirst();
            return;
        }
    }


    @FXML
    private GridPane roomBox;
    @FXML
    public void cart() throws IOException, SQLException {
        if (roomBooking.isEmpty()) {
            Dialog.showWarning("Warning", null, "Your cart is empty");
            return;
        }
        cartPane.setVisible(true);
        roomBox.getChildren().clear();

        int column = 0;
        int row = 1;
        for (Room room : roomBooking) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/mycompany/app/views/guest/card.fxml"));
            VBox vBox = fxmlLoader.load();
            CardController card = fxmlLoader.getController();
            card.setData(room);
            if (column == 3) {
                column = 0;
                row++;
                containPane.setPrefHeight(containPane.getPrefHeight() + 150);
            }
            roomBox.add(vBox, column++, row);
            System.out.println(roomBox.getChildren().size());
            GridPane.setMargin(vBox, new Insets(25));
        }
    }


    @FXML
    void reservation() {

    }

    @FXML
    void closeCart() {
        cartPane.setVisible(false);
    }

    public void reload(ActionEvent actionEvent) throws SQLException {
        initialize();
    }
}