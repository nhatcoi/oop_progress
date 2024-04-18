package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.controllers.guest.PaymentController;
import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.reservations;

public class ResManageController extends OverviewController  {
    public TextField tfSearchOrderCode;
    public Label lbRevenue;
    public Label lbTotalBill;
    public Label lbRoomName;
    public Label lbRoomCode;
    public Label lbCount;
    @FXML
    private TableView<Payment> tableViewPayment;
    @FXML
    private TableView<Reservation> tableViewRes;
    @FXML
    private AnchorPane resManagePane;

    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    ObservableList<Payment> payments = FXCollections.observableArrayList();

    Connection connect;
    public void initialize() throws SQLException {
        getAllReservation();
        getAllPayment();
        tableViewRes.setItems(reservations);
        tableViewPayment.setItems(payments);

        Map<Integer, Integer> roomCount = new HashMap<>();
        int maxRoomId = -1;
        int maxCount = 0;
        for(Reservation res : reservations) {
            int roomId = res.getRoom_id();
            roomCount.put(roomId, roomCount.getOrDefault(roomId, 0) + 1);
            int count = roomCount.get(roomId);
            if(count > maxCount) {
                maxCount = count;
                maxRoomId = roomId;
            }
        }

        if(maxRoomId != -1) {
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
        for(Payment pay : payments) {
            totalRevenue += pay.getTotalPrice();
        }
        lbRevenue.setText(String.valueOf(totalRevenue));
        lbTotalBill.setText(String.valueOf(payments.size()));
    }

    private void getAllReservation() throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT * FROM reservations";
        assert connect != null;
        ResultSet rs = connect.createStatement().executeQuery(sql);
        while(rs.next()) {
            reservations.add(new Reservation(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("room_id"), rs.getDate("check_in_date"), rs.getDate("check_out_date")));
        }
    }

    private void getAllPayment() throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT * FROM payments";
        assert connect != null;
        ResultSet rs = connect.createStatement().executeQuery(sql);
        while(rs.next()) {
            payments.add(new Payment(rs.getInt("id"), rs.getInt("reservation_id"), rs.getDouble("total_price"), rs.getString("payment_method"), rs.getDate("payment_date")));
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
                    String id = String.valueOf(reservations.get(i).getId());
                    if(search.contains(id)) {
                        searchRes.add(reservations.get(i));
                    }
                    String id2 = String.valueOf(payments.get(i).getReservationId());
                    if(search.contains(id2)) {
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




}
