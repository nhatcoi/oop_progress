package com.mycompany.app.hotel_management.controllers.manager.admin;

import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lombok.var;

import java.sql.Connection;

public class StaffProfileController extends EditStaffController {
    public ImageView imgAvt;
    public AnchorPane profilePane;
    @FXML
    private Label lbStartDate;

    @FXML
    private Label lbPosition;

    @FXML
    private Label lbEndDate;

    @FXML
    private Label lbStaffName;

    @FXML
    private Label lbPhone;

    @FXML
    private Label lbAddress;

    @FXML
    private Label lbEmail;

    Connection connect;

    public void initialize() {
        connect = Database.connectDb();
        System.out.println(idStaff);
        String sql = "SELECT * FROM staff WHERE id = " + idStaff;
        assert connect != null;
        try {
            var statement = connect.createStatement();
            var resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                lbStaffName.setText(resultSet.getString("name"));
                lbPosition.setText(resultSet.getString("position"));
                lbStartDate.setText(resultSet.getString("start_date"));
                lbEndDate.setText(resultSet.getString("end_date"));
                lbPhone.setText(resultSet.getString("phone"));
                lbAddress.setText(resultSet.getString("address"));
                lbEmail.setText(resultSet.getString("email"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public void changePhoto() {

    }

    public void esc() {
        ToolFXML.closeFXML(profilePane);
    }

    public void escKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE)
            esc();
    }
}
