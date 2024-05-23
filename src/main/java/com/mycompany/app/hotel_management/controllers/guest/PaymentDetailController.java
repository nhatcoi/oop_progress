package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.FilesUtils;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDetailController extends PaymentController {
    @FXML
    private AnchorPane billPaymentPane;
    @FXML
    private Label lbTotal, lbAllTotal, lbPaymentMethod, lbResId, lbSignature, lbPrice, lbPhone, lbRoomName, lbGuestBill, lbEmail, lbDays, lbCheckIn, lbCheckOut;

    private Connection connect;

    public void initialize() throws SQLException {
        Room room = rooms.stream()
                .filter(r -> r.getId() == reservation.getRoom_id())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Room not found"));

        updateReservationDetails();
        setDays(room);
        setTotal(room);
        Payment payment = new Payment();
        fetchPayment(payment);
        updatePaymentDetails(room, payment);
    }

    void fetchPayment(Payment payment) throws SQLException {
        String sql = "SELECT * FROM payments WHERE reservation_id = ?";
        try (Connection connect = Database.connectDb();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, reservation.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    payment.setId(resultSet.getInt("id"));
                    payment.setReservationId(resultSet.getInt("reservation_id"));
                    payment.setTotalPrice(resultSet.getDouble("total_price"));
                    payment.setPaymentMethod(resultSet.getString("payment_method"));
                    payment.setPaymentDate(resultSet.getDate("payment_date"));
                }
            }
        }
    }

    private void setTotal(Room room) {
        double total = room.getPrice() * Integer.parseInt(lbDays.getText());
        lbTotal.setText(String.valueOf(total));
    }

    private void setDays(Room room) {
        lbRoomName.setText(room.getName());
        long millisecondsPerDay = 1000 * 60 * 60 * 24;
        long checkInTime = reservation.getCheckInDate().getTime();
        long checkoutTime = reservation.getCheckoutDate().getTime();
        long daysBetween = (checkoutTime - checkInTime) / millisecondsPerDay;
        lbDays.setText(String.valueOf(daysBetween + 1));
    }

    private void updateReservationDetails() {
        lbResId.setText("#" + reservation.getId());
        lbCheckIn.setText(String.valueOf(reservation.getCheckInDate()));
        lbCheckOut.setText(String.valueOf(reservation.getCheckoutDate()));
    }

    private void updatePaymentDetails(Room room, Payment payment) {
        lbGuestBill.setText(guest.getName());
        lbPhone.setText(guest.getPhone());
        lbEmail.setText(guest.getEmail());
        lbPrice.setText(String.valueOf(room.getPrice()));
        lbTotal.setText(String.valueOf(room.getPrice()));
        lbAllTotal.setText(String.valueOf(payment.getTotalPrice()));
        lbPaymentMethod.setText(payment.getPaymentMethod());
        lbSignature.setText(guest.getName());
    }

    @FXML
    private void closeBill() {
        ToolFXML.closeFXML(billPaymentPane);
    }

    @FXML
    private void escKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            closeBill();
        }
    }

    @FXML
    private void scan() {
        FilesUtils.scanToPDF((Stage) billPaymentPane.getScene().getWindow());
        FilesUtils.openPDF("scan_bill_vn_hotel.pdf");
    }
}
