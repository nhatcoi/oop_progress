package com.mycompany.app.hotel_management.controllers.guest;


import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.controllers.ManagerController;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.mycompany.app.hotel_management.controllers.ManagerController.user;

public class EditProfileController extends GuestController {

    public Label lbName;
    public TextField tfName;
    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfAddress;


    @FXML
    private Label lbPhone;

    @FXML
    private Label lbAddress;


    @FXML
    private Label lbUsername;
    public Connection connect;

    public void initialize() {
        super.initialize();
        if (lbUsername != null) {
            lbUsername.setText(user.getUsername());
        }
        if (guest != null) {
            if (lbPhone != null) {
                lbPhone.setText(guest.getPhone());
            }
            if (lbAddress != null) {
                lbAddress.setText(guest.getAddress());
            }
            if (lbName != null) {
                lbName.setText(guest.getName());
            }
        }
    }

    @FXML
    void openPassword(ActionEvent event) {

    }

    @FXML
    void changeAvatar(ActionEvent event) {

    }

    @FXML
    void openInfo(ActionEvent event) {

    }

    @FXML
    void out() throws IOException {
        super.signOut();
    }

    public void updateInfo(ActionEvent actionEvent) throws SQLException {
        if (!tfPhone.getText().isEmpty()) {
            guest.setPhone(tfPhone.getText());
        }
        if (!tfAddress.getText().isEmpty()) {
            guest.setAddress(tfAddress.getText());
        }
        if (!tfName.getText().isEmpty()) {
            guest.setName(tfName.getText());
        }

        connect = Database.connectDb();
        String sql = "SELECT * FROM guests WHERE user_id = '" + user.getId() + "'";
        assert connect != null;
        Statement statement = connect.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next())
        {
            sql = "UPDATE guests SET name = '" + guest.getName() + "', phone = '" + guest.getPhone() + "', address = '" + guest.getAddress() + "' WHERE user_id = '" + user.getId() + "'";
            statement.executeUpdate(sql);
        }
        else{
            sql = "INSERT INTO guests (user_id, name, phone, address) VALUES ('" + user.getId() + "', '" + guest.getName() + "', '" + guest.getPhone() + "', '" + guest.getAddress() + "')";
            statement.executeUpdate(sql);
        }

        lbName.setText(tfName.getText());
        lbPhone.setText(tfPhone.getText());
        lbAddress.setText(tfAddress.getText());

    }
}
