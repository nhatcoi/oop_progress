package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

import static com.mycompany.app.hotel_management.controllers.guest.HomeController.imageCache;

public class ResManageController extends OverviewController  {
    @FXML
    private TextField tfSearchOrderCode;
    @FXML
    private Label lbRevenue;
    @FXML
    private Label lbTotalBill;
    @FXML
    private Label lbRoomName;
    @FXML
    private Label lbRoomCode;
    @FXML
    private Label lbCount;
    @FXML
    private Label lbIdRes;
    @FXML
    private Label lbGuestCode;
    @FXML
    private Label lbRoom;
    @FXML
    private Label lbCheckIn;
    @FXML
    private Label lbCheckOut;
    @FXML
    private ImageView imgRoom;
    @FXML
    private AnchorPane detailResPane;
    @FXML
    private AnchorPane managePane;
    @FXML
    private TableView<Payment> tableViewPayment;
    @FXML
    private TableView<Reservation> tableViewRes;
    @FXML
    private AnchorPane resManagePane;


    private Connection connect;
    RoomServiceImpl sv = new RoomServiceImpl();

    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    ObservableList<Payment> payments = FXCollections.observableArrayList();
    private Reservation resDetail;

    public void initialize() throws SQLException, ParseException {
        show(resManagePane);
        getAllReservation();
        getAllPayment();
        tableViewRes.setItems(reservations);
        tableViewPayment.setItems(payments);

        tableViewRes.setOnMouseClicked(event -> {
            resDetail = tableViewRes.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && resDetail != null) {
                updateReservationDetails(resDetail);
            }
        });

        updateRoomStatistics();
        updateRevenueStatistics();
        refreshTable();
    }

    private void updateReservationDetails(Reservation resDetail) {
        lbIdRes.setText(String.valueOf(resDetail.getId()));
        lbGuestCode.setText(String.valueOf(resDetail.getUser_id()));
        lbRoom.setText(roomList.get(indexOfRoom(resDetail)).getName());
        lbCheckIn.setText(String.valueOf(resDetail.getCheckInDate()));
        lbCheckOut.setText(String.valueOf(resDetail.getCheckoutDate()));

        int roomIndex = indexOfRoom(resDetail);
        Image img = imageCache.get(roomIndex);
        if (img == null) {
            img = sv.fetchImageRoom(roomList.get(roomIndex));
            imageCache.put(roomIndex, img);
        }
        imgRoom.setImage(img);
        show(detailResPane);
    }

    private void updateRoomStatistics() throws SQLException {
        Map<Integer, Integer> roomCount = new HashMap<>();
        int maxRoomId = -1;
        int maxCount = 0;

        for (Reservation res : reservations) {
            int roomId = res.getRoom_id();
            roomCount.put(roomId, roomCount.getOrDefault(roomId, 0) + 1);
            int count = roomCount.get(roomId);
            if (count > maxCount) {
                maxCount = count;
                maxRoomId = roomId;
            }
        }

        if (maxRoomId != -1) {
            lbRoomCode.setText(String.valueOf(maxRoomId));
            String roomName = getRoomNameById(maxRoomId);
            lbRoomName.setText(roomName);
            lbCount.setText(String.valueOf(maxCount));
        }
    }

    private String getRoomNameById(int roomId) throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT name FROM rooms WHERE id = ?";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        }
        return null;
    }

    private void updateRevenueStatistics() {
        double totalRevenue = payments.stream().mapToDouble(Payment::getTotalPrice).sum();
        lbRevenue.setText(String.valueOf(totalRevenue));
        lbTotalBill.setText(String.valueOf(payments.size()));
    }

    @FXML
    private void refreshTable() throws SQLException, ParseException {
        resSetOfDate();
    }

    private void resSetOfDate() throws SQLException, ParseException {
        ObservableList<Integer> roomsToAvailable = FXCollections.observableArrayList();
        ObservableList<Integer> roomsToOccupied = FXCollections.observableArrayList();

        for (Reservation res : reservations) {
            Timestamp checkoutDate = (Timestamp) res.getCheckoutDate();
            Timestamp checkInDate = (Timestamp) res.getCheckInDate();
            int roomId = indexOfRoom(res);
            if (isCheckoutPastToday(checkoutDate) || isCheckinNextDay(checkInDate)) {
                roomsToAvailable.add(roomId);
            } else {
                roomsToOccupied.add(roomId);
            }
        }

        updateRoomStatuses(roomsToAvailable, RoomStatus.AVAILABLE.ordinal());
        updateRoomStatuses(roomsToOccupied, RoomStatus.OCCUPIED.ordinal());
    }

    private boolean isCheckoutPastToday(Timestamp dateRes) {
        return dateRes.before(Timestamp.from(Instant.now()));
    }

    private boolean isCheckinNextDay(Timestamp dateRes) {
        return dateRes.after(Timestamp.from(Instant.now()));
    }

    private void updateRoomStatuses(ObservableList<Integer> roomIds, int statusRoom) throws SQLException {
        if (roomIds.isEmpty()) return;

        connect = Database.connectDb();
        String sql = "UPDATE rooms SET status = ? WHERE id IN (" + String.join(",", Collections.nCopies(roomIds.size(), "?")) + ")";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, statusRoom);
            for (int i = 0; i < roomIds.size(); i++) {
                stmt.setInt(i + 2, roomList.get(roomIds.get(i)).getId());
            }
            stmt.executeUpdate();
        }
    }

    private int indexOfRoom(Reservation res) {
        return IntStream.range(0, roomList.size())
                .filter(i -> roomList.get(i).getId() == res.getRoom_id())
                .findFirst()
                .orElse(-1);
    }

    private void getAllReservation() throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT * FROM reservations";
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(new Reservation(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("room_id"), rs.getTimestamp("check_in_date"), rs.getTimestamp("check_out_date")));
            }
        }
    }

    private void getAllPayment() throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT * FROM payments";
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                payments.add(new Payment(rs.getInt("id"), rs.getInt("reservation_id"), rs.getDouble("total_price"), rs.getString("payment_method"), rs.getTimestamp("payment_date")));
            }
        }
    }

    @FXML
    void searchRes(ActionEvent actionEvent) throws SQLException {
        String search = tfSearchOrderCode.getText().trim();
        if (search.isEmpty()) {
            // If search field is empty, reset the table items to the full list
            tableViewRes.setItems(reservations);
            tableViewPayment.setItems(payments);
        } else {
            // If search field is not empty, perform the search
            ObservableList<Reservation> searchRes = FXCollections.observableArrayList();
            ObservableList<Payment> searchPay = FXCollections.observableArrayList();

            // Search reservations
            for (Reservation res : reservations) {
                if (String.valueOf(res.getId()).contains(search)) {
                    searchRes.add(res);
                }
            }

            // Search payments
            for (Payment pay : payments) {
                if (String.valueOf(pay.getReservationId()).contains(search)) {
                    searchPay.add(pay);
                }
            }

            // Set the filtered results to the table views
            tableViewRes.setItems(searchRes);
            tableViewPayment.setItems(searchPay);
        }
    }


    @FXML
    void outDetail() {
        detailResPane.setVisible(false);
    }

    void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(detailResPane, managePane);
        for (AnchorPane pane : allPanes) {
            if(pane.equals(paneToShow)) {
                pane.setVisible(true);
            }
        }
    }

    @FXML
    void esc(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE)
            outDetail();
    }
}
