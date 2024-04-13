package com.mycompany.app.hotel_management.controllers.guest;


import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.controllers.ManagerController;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.Md5;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;

import static com.mycompany.app.hotel_management.controllers.ManagerController.user;

public class EditProfileController extends GuestController {

    public Label lbName;
    public TextField tfName;
    public AnchorPane passwordPane;
    public AnchorPane infoPane;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfNewPassword;
    @FXML
    private PasswordField tfConfirmPassword;
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

    public void initialize() throws IOException, SQLException {
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
        passwordPane.setVisible(true);
        infoPane.setVisible(false);
    }

    @FXML
    void changeAvatar(ActionEvent event) {

    }

    @FXML
    void openInfo(ActionEvent event) {
        infoPane.setVisible(true);
        passwordPane.setVisible(false);
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

    public void updatePassword(ActionEvent event) throws SQLException {
        if(tfPassword.getText().isEmpty()) {
            Dialog.showError("Error", null, "Please enter your current password");
            return;
        }
        if(tfNewPassword.getText().isEmpty()) {
            Dialog.showError("Error", null, "Please enter new password");
            return;
        }
        if(tfConfirmPassword.getText().isEmpty()) {
            Dialog.showError("Error", null, "Please enter confirm password");
            return;
        }
        if(tfNewPassword.equals(tfConfirmPassword)) {
            Dialog.showError("Error", null, "Confirm password is not match with new password");
            return;
        }
        if(tfNewPassword.equals(tfPassword)) {
            Dialog.showError("Error", null, "New password is the same as the old password");
            return;
        }
        if(tfNewPassword.getText().length() < 6) {
            Dialog.showError("Error", null, "Password must be at least 6 characters");
            return;
        }

        String password = Md5.hashString(tfPassword.getText());
        String newPassword = Md5.hashString(tfNewPassword.getText());

        connect = Database.connectDb();

        String sql0 = "SELECT * FROM guests WHERE user_id = '" + user.getId() + "'";
        assert connect != null;
        Statement statement2 = connect.createStatement();
        ResultSet resultSet2 = statement2.executeQuery(sql0);
        if(resultSet2.next())
            guest.setUser_id(resultSet2.getInt("user_id"));

        String sql = "SELECT * FROM users WHERE id = '" + guest.getUser_id() + "' AND password = '" + password + "'";
        assert connect != null;
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()) {
            sql = "UPDATE users SET password = '" + newPassword + "' WHERE id = '" + guest.getUser_id() + "'";
            statement.executeUpdate(sql);
            Dialog.showInformation("Success", null, "Password updated successfully");
            return;
        } else {
            Dialog.showError("Error", null, "Current password is incorrect");
            return;
        }

    }
}