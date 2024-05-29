package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.entities.Payment;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.text.DecimalFormat;

public class BankController extends PaymentDetailController{
    @FXML
    private AnchorPane bankPane;
    @FXML
    private Label lbBankMoney;
    @FXML
    private Label lbNote;

    final double VND = 25445;

    public void initialize() throws SQLException {
        Payment payment = new Payment();
        fetchPayment(payment);
        String totalPrice = formatPrice(payment.getTotalPrice() * VND) + " VND";
        lbBankMoney.setText(totalPrice);
        lbNote.setText(String.valueOf(reservation.getId()));
    }

    public static String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(price);
    }

    public void finished() {
        ToolFXML.closeFXML(bankPane);
    }
}
