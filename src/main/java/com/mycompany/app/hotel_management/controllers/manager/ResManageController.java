package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.repositories.Database;
import com.sun.glass.events.KeyEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import lombok.var;

import java.security.Key;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;

public class ResManageController extends OverviewController  {
    public TextField tfSearchOrderCode;
    public Label lbRevenue;
    public Label lbTotalBill;
    public Label lbRoomName;
    public Label lbRoomCode;
    public Label lbCount;
    public Label lbIdRes;
    public Label lbGuestCode;
    public Label lbRoom;
    public Label lbCheckIn;
    public Label lbCheckOut;
    public ImageView imgRoom;
    public AnchorPane detailResPane;
    public AnchorPane managePane;
    @FXML
    private TableView<Payment> tableViewPayment;
    @FXML
    private TableView<Reservation> tableViewRes;
    @FXML
    private AnchorPane resManagePane;

    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    ObservableList<Payment> payments = FXCollections.observableArrayList();

    Connection connect;
    Reservation resDetail;

    public void initialize() throws SQLException, ParseException {
        show(resManagePane);
        getAllReservation();
        getAllPayment();
        tableViewRes.setItems(reservations);
        tableViewPayment.setItems(payments);

        tableViewRes.setOnMouseClicked(event -> {
            resDetail = tableViewRes.getSelectionModel().getSelectedItem();
            if(event.getClickCount() == 2 && resDetail != null) {
                lbIdRes.setText(String.valueOf(resDetail.getId()));
                lbGuestCode.setText(String.valueOf(resDetail.getUser_id()));
                lbRoom.setText(String.valueOf(roomList.get(indexOfRoom(resDetail)).getName()));
                lbCheckIn.setText(String.valueOf(resDetail.getCheckInDate()));
                lbCheckOut.setText(String.valueOf(resDetail.getCheckoutDate()));
                imgRoom.setImage(images.get(indexOfRoom(resDetail)));
                show(detailResPane);
            }
        });

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
            connect = Database.connectDb();
            String sql = "SELECT name FROM rooms WHERE id = " + maxRoomId;
            assert connect != null;
            ResultSet rs = connect.createStatement().executeQuery(sql);
            rs.next();
            lbRoomName.setText(rs.getString("name"));
            lbCount.setText(String.valueOf(maxCount));
        }


        double totalRevenue = 0;
        for (Payment pay : payments) {
            totalRevenue += pay.getTotalPrice();
        }
        lbRevenue.setText(String.valueOf(totalRevenue));
        lbTotalBill.setText(String.valueOf(payments.size()));

//        resSetOfDate();
    }

    public void refreshTable() throws SQLException, ParseException {
        resSetOfDate();
    }

    private void resSetOfDate() throws SQLException, ParseException {
        ObservableList<Integer> roomsToAvailable = FXCollections.observableArrayList();
        ObservableList<Integer> roomsToOccupied = FXCollections.observableArrayList();

        for (Reservation resNotOfDate : reservations) {
            Timestamp checkoutDate = (Timestamp) resNotOfDate.getCheckoutDate();
            Timestamp checkInDate = (Timestamp) resNotOfDate.getCheckInDate();
            // Kiểm tra nếu checkout đã qua hoặc checkin là hôm sau của hiện tại
            if (isCheckoutPastToday(checkoutDate) || isCheckinNextDay(checkInDate)) {
                int roomId = indexOfRoom(resNotOfDate);
                roomsToAvailable.add(roomId);
                System.out.println(roomList.get(roomId).getStatus() + " is out of date : " + roomId);
            } else {
                roomsToOccupied.add(indexOfRoom(resNotOfDate));
            }
        }
        // Thực hiện cập nhật trạng thái của các phòng trong danh sách
        if (!roomsToAvailable.isEmpty()) {
            updateStatusBatch(roomsToAvailable, RoomStatus.AVAILABLE.ordinal());
            updateStatusBatch(roomsToOccupied, RoomStatus.OCCUPIED.ordinal());
        }
    }

    private boolean isCheckoutPastToday(Timestamp dateRes) {
        Instant instant = Instant.now();
        // Chuyển đổi Instant thành Timestamp
        Timestamp currentDate = Timestamp.from(instant);
        System.out.println("\ninstant" + instant);
        System.out.println("time stamp"+ currentDate);
        System.out.println("date res"+dateRes);
        return dateRes.before(currentDate);
    }

    private boolean isCheckinNextDay(Timestamp dateRes) {
        Instant instant = Instant.now();
        // Chuyển đổi Instant thành Timestamp
        Timestamp currentDate = Timestamp.from(instant);
        return dateRes.after(currentDate);
    }

    private void updateStatusBatch(ObservableList<Integer> roomIds, int statusRoom) throws SQLException {
        connect = Database.connectDb();
        String sql = "UPDATE rooms SET status = ? WHERE id IN (" + String.join(",", Collections.nCopies(roomIds.size(), "?")) + ")";
        try {
            assert connect != null;
            var prepare = connect.prepareStatement(sql);
            prepare.setInt(1, statusRoom);
            int index = 2;
            for (int roomId : roomIds) {
                prepare.setInt(index++, roomList.get(roomId).getId());
            }
            prepare.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        assert connect != null;
        ResultSet rs = connect.createStatement().executeQuery(sql);
        while(rs.next()) {
            reservations.add(new Reservation(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("room_id"), rs.getTimestamp("check_in_date"), rs.getTimestamp("check_out_date")));
        }
    }

    private void getAllPayment() throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT * FROM payments";
        assert connect != null;
        ResultSet rs = connect.createStatement().executeQuery(sql);
        while(rs.next()) {
            payments.add(new Payment(rs.getInt("id"), rs.getInt("reservation_id"), rs.getDouble("total_price"), rs.getString("payment_method"), rs.getTimestamp("payment_date")));
        }
    }

    public void searchRes(ActionEvent actionEvent) throws SQLException {
        String search = tfSearchOrderCode.getText();
        if(search.isEmpty()) {
            tableViewRes.setItems(reservations);
            tableViewPayment.setItems(payments);
        } else {
            tableViewRes.setItems(null);
            tableViewPayment.setItems(null);
            try {
                ObservableList<Reservation> searchRes = FXCollections.observableArrayList();
                ObservableList<Payment> searchPay = FXCollections.observableArrayList();
                for(int i = 0; i < reservations.size(); i++) {
                    String id = String.valueOf(reservations.get(i).getId()).trim(),
                           id2 = String.valueOf(payments.get(i).getReservationId()).trim();
                    if(id.contains(search)) {
                        searchRes.add(reservations.get(i));
                    }
                    if(id2.contains(search)) {
                        searchPay.add(payments.get(i));
                    }
                }
                tableViewRes.setItems(searchRes);
                tableViewPayment.setItems(searchPay);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    public void outDetail() {
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

    public void esc(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE)
            outDetail();
    }
}
