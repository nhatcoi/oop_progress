package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.entities.Reservation;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.PDFScanner;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Label lbTotal;

    @FXML
    private Label lbRoomName11;

    @FXML
    private Label lbAllTotal;

    @FXML
    private Label lbRoomName2;

    @FXML
    private Label lbRoomName1;

    @FXML
    private Label lbPaymentMethod;

    @FXML
    private Label lbResId;

    @FXML
    private Label lbSignature;

    @FXML
    private Label lbPrice;

    @FXML
    private Label lbPhone;

    @FXML
    private Label lbRoomName;

    @FXML
    private Label lbNameBill;

    @FXML
    private Label lbGuestBill;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbDays;

    @FXML
    private Label lbCheckIn;

    @FXML
    private Label lbCheckOut;

    Connection connect;
    public void initialize() throws SQLException {
        long startTime = System.nanoTime();

        Room room = rooms.stream().filter(r -> r.getId() == reservation.getRoom_id()).findFirst().orElse(null);
        assert room != null;
        lbGuestBill.setText(guest.getName());
        lbPhone.setText(guest.getPhone());
        lbEmail.setText(guest.getEmail());
        reservation(reservation);
        setDays(room);
        lbPrice.setText(String.valueOf(room.getPrice()));
        lbTotal.setText(String.valueOf(room.getPrice()));
        setTotal(room);

        Payment payment = new Payment();
        fetchPayment(payment);
        lbAllTotal.setText(String.valueOf(payment.getTotalPrice()));
        lbPaymentMethod.setText(payment.getPaymentMethod());
        lbSignature.setText(guest.getName());


        ToolFXML.test("PaymentDetail : ", startTime);
    }

    void fetchPayment(Payment payment) {
        try{
            connect = Database.connectDb();
            assert connect != null;
            String sql = "SELECT * FROM payments WHERE reservation_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, reservation.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                payment.setId(resultSet.getInt("id"));
                payment.setReservationId(resultSet.getInt("reservation_id"));
                payment.setTotalPrice(resultSet.getDouble("total_price"));
                payment.setPaymentMethod(resultSet.getString("payment_method"));
                payment.setPaymentDate(resultSet.getDate("payment_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void setTotal(Room room) {
        double total = room.getPrice() * Integer.parseInt(lbDays.getText());
        lbTotal.setText(String.valueOf(total));
    }

    void setDays(Room room) {
        lbRoomName.setText(room.getName());
        long millisecondsPerDay = 1000 * 60 * 60 * 24;
        long checkInTime = reservation.getCheckInDate().getTime();
        long checkoutTime = reservation.getCheckoutDate().getTime();
        long daysBetween = (checkoutTime - checkInTime) / millisecondsPerDay;
        lbDays.setText(String.valueOf(daysBetween+1));
    }


    void reservation (Reservation res) {
        lbResId.setText(String.valueOf(res.getId()));
        lbCheckIn.setText(String.valueOf(res.getCheckInDate()));
        lbCheckOut.setText(String.valueOf(res.getCheckoutDate()));
    }
    @FXML
    void closeBill() {
        ToolFXML.closeFXML(billPaymentPane);
    }

    @FXML
    void scan() {
        PDFScanner.scanToPDF((Stage) billPaymentPane.getScene().getWindow());
        PDFScanner.openPDF("scan_bill_vn_hotel.pdf");
    }
}
