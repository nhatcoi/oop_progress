package com.mycompany.app.hotel_management.controllers.guest;


import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.Md5;
import com.mycompany.app.hotel_management.utils.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;


import static com.mycompany.app.hotel_management.controllers.ManagerController.user;


public class EditProfileController extends GuestController {



    public Label lbName;
    public TextField tfName;
    public AnchorPane passwordPane;
    public AnchorPane infoPane;
    public TextField tfEmail;
    public Label lbEmail;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private PasswordField pfNewPassword;
    @FXML
    private PasswordField pfConfirmPassword;
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
            if (lbEmail != null) {
                lbEmail.setText(guest.getEmail());
            }
            System.out.println(guest.toString());
        }
    }

    @FXML
    void openPassword() {
        passwordPane.setVisible(true);
        infoPane.setVisible(false);
    }

    @FXML
    void changeAvatar() {

    }

    @FXML
    void openInfo() {
        infoPane.setVisible(true);
        passwordPane.setVisible(false);
    }


    public void updateInfo() throws SQLException {
        if (!tfPhone.getText().isEmpty()) {
            guest.setPhone(tfPhone.getText());
        }
        if (!tfAddress.getText().isEmpty()) {
            guest.setAddress(tfAddress.getText());
        }
        if (!tfName.getText().isEmpty()) {
            guest.setName(tfName.getText());
        }
        if(!tfEmail.getText().isEmpty()) {
            guest.setEmail(tfEmail.getText());
            if(Validate.validateEmail(tfEmail.getText())) {
                Dialog.showError("Error", null, "Email is invalid");
                return;
            }
        }

        connect = Database.connectDb();
        String sql = "SELECT * FROM guests WHERE user_id = '" + user.getId() + "'";
        assert connect != null;
        Statement statement = connect.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next())
        {
            sql = "UPDATE guests SET name = '" + guest.getName() + "', phone = '" + guest.getPhone() + "', address = '" + guest.getAddress() + "', email = '" + guest.getEmail() + "' WHERE user_id = '" + user.getId() + "'";
            statement.executeUpdate(sql);
        }
        else{
            sql = "INSERT INTO guests (user_id, name, phone, address, email) VALUES ('" + user.getId() + "', '" + guest.getName() + "', '" + guest.getPhone() + "', '" + guest.getAddress() + "', '" + guest.getEmail() + "')";
            statement.executeUpdate(sql);
        }

        lbName.setText(tfName.getText());
        lbPhone.setText(tfPhone.getText());
        lbAddress.setText(tfAddress.getText());
    }

    public void updatePassword() throws SQLException {
        String pass = pfPassword.getText();
        String newPass = pfNewPassword.getText();
        String confirmPass = pfConfirmPassword.getText();

        if(pass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter your current password");
            return;
        }
        if(newPass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter new password");
            return;
        }
        if(confirmPass.isEmpty()) {
            Dialog.showError("Error", null, "Please enter confirm password");
            return;
        }
        if(!(newPass.equals(confirmPass))) {
            Dialog.showError("Error", null, "Confirm password is not match with new password");
            return;
        }
        if(newPass.equals(pass)) {
            Dialog.showError("Error", null, "New password is the same as the old password");
            return;
        }
        if(newPass.length() < 6) {
            Dialog.showError("Error", null, "Password must be at least 6 characters");
            return;
        }

        String password = Md5.hashString(pass);
        String newPassword = Md5.hashString(newPass);

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

    public void phoneSupport() {
        String phoneNumber = "0376696037";
        String facetimeURL = "facetime://" + phoneNumber;

        try {
            Desktop.getDesktop().browse(new URI(facetimeURL));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void cusSupport() {
        String mail = "23010887@st.phenikaa-uni.edu.vn";
        String cusSupport = "mailto:" + mail;
        try{
            Desktop.getDesktop().browse(new URI(cusSupport));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void contactSupport() {
        String mail = "23010636@st.phenikaa-uni.edu.vn";
        String cusSupport = "mailto:" + mail;
        try{
            Desktop.getDesktop().browse(new URI(cusSupport));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void aboutUs() {
        String url = "https://github.com/nhatcoi/project_java";
        openBrowser(url);
    }

    public void address() {
        String url = "https://s.net.vn/D9Nc";
        openBrowser(url);
    }

    private void openBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}