package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.service.Impl.RoomServiceImpl;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.entities.User;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.*;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AuthController {

    @FXML
    private TextField tfEmail, tfUsername, username, signup_username;
    @FXML
    private PasswordField password, signup_password, signup_password2;
    @FXML
    private StackPane authForm;
    @FXML
    private AnchorPane login_form, signup_form, forgot_form;
    @FXML
    private Hyperlink forgotPassword, createAccount, signup_forgot, signup_login, forgot_login, forgot_signup;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private RoomServiceImpl roomService = new RoomServiceImpl();
    public static ObservableList<Room> roomsIni = FXCollections.observableArrayList();

    public void login() {
        String userLog = username.getText();
        String passLog = password.getText();

        if (userLog.isEmpty() || passLog.isEmpty()) {
            Dialog.showError("Login failed", null, "Please fill in all fields");
            return;
        }

        try {
            connect = Database.connectDb();
            passLog = Md5.hashString(passLog);
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, userLog);
            prepare.setString(2, passLog);
            result = prepare.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                int role = result.getInt("role");
                ManagerController.user = new User(id, userLog, role);

                Dialog.showInformation("Login successful", null, "Welcome " + userLog);
                handleUserRole(role);
                ToolFXML.closeFXML(authForm);
            } else {
                Dialog.showError("Login failed", null, "Username or password is incorrect");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleUserRole(int role) throws SQLException, IOException {
        if (role == UserRole.GUEST.getValue()) {
            loadGuestData();
            ToolFXML.openFXML("views/guestForm.fxml");
        } else {
            ToolFXML.openFXML("views/manager.fxml");
        }
    }

    private void loadGuestData() throws SQLException {
        String sql2 = "SELECT * FROM guests WHERE user_id = ?";
        prepare = connect.prepareStatement(sql2);
        prepare.setInt(1, ManagerController.user.getId());
        result = prepare.executeQuery();
        if (result.next()) {
            GuestController.guest.setId(result.getInt("id"));
            GuestController.guest.setName(result.getString("name"));
            GuestController.guest.setPhone(result.getString("phone"));
            GuestController.guest.setAddress(result.getString("address"));
            GuestController.guest.setEmail(result.getString("email"));
            GuestController.guest.setUser_id(result.getInt("user_id"));
        }
    }

    public void signup() {
        String username = signup_username.getText();
        String password = signup_password.getText();
        String confirmPassword = signup_password2.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Dialog.showError("Sign up failed", null, "Please fill in all fields");
            return;
        }

        if (username.length() < 6) {
            Dialog.showError("Error", null, "Username must be at least 6 characters");
            return;
        }

        if (password.length() < 6) {
            Dialog.showError("Error", null, "Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            Dialog.showError("Error", null, "Confirm password does not match");
            return;
        }

        try {
            connect = Database.connectDb();
            if (isUsernameTaken(username)) {
                Dialog.showError("Error", null, "Username is already taken");
                return;
            }

            password = Md5.hashString(password);
            addUser(username, password);
            Dialog.showInformation("Success", null, "Account created successfully");

            this.signup_form.setVisible(false);
            this.login_form.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isUsernameTaken(String username) throws SQLException {
        String checkSql = "SELECT * FROM users WHERE username = ?";
        prepare = connect.prepareStatement(checkSql);
        prepare.setString(1, username);
        ResultSet checkResult = prepare.executeQuery();
        return checkResult.next();
    }

    private void addUser(String username, String password) throws SQLException {
        String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
        prepare = connect.prepareStatement(insertSql);
        prepare.setString(1, username);
        prepare.setString(2, password);
        prepare.executeUpdate();
    }

    public void requestPassword() {
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        if (!Security.validateEmail(email)) {
            Dialog.showError("Error", null, "Email is invalid");
            return;
        }

        try {
            connect = Database.connectDb();
            String sql = "SELECT * FROM guests WHERE email = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, email);
            result = prepare.executeQuery();

            if (result.next()) {
                String newPassword = Security.generateRandomString(6);
                String hashedPassword = Md5.hashString(newPassword);

                updatePassword(username, hashedPassword);
                MailSender.sendEmail(email, "New password", "Your new password is: " + newPassword);
                Dialog.showInformation("Success", null, "Your new password has been sent to your email");
            } else {
                Dialog.showError("Error", null, "Email not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePassword(String username, String hashedPassword) throws SQLException {
        String updateSql = "UPDATE users SET password = ? WHERE username = ?";
        prepare = connect.prepareStatement(updateSql);
        prepare.setString(1, hashedPassword);
        prepare.setString(2, username);
        prepare.executeUpdate();
    }

    public void exit() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) authForm.getScene().getWindow();
        stage.setIconified(true);
    }

    public void switchForm(ActionEvent event) {
        login_form.setVisible(false);
        signup_form.setVisible(false);
        forgot_form.setVisible(false);

        if (event.getSource() == signup_login || event.getSource() == forgot_login) {
            showForm(login_form);
        } else if (event.getSource() == createAccount || event.getSource() == forgot_signup) {
            showForm(signup_form);
        } else if (event.getSource() == forgotPassword || event.getSource() == signup_forgot) {
            showForm(forgot_form);
        }
    }

    private void showForm(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(login_form, signup_form, forgot_form);
        for (AnchorPane pane : allPanes) {
            pane.setVisible(pane == paneToShow);
        }
    }

    @FXML
    public void initialize() throws SQLException, ParseException {
        connect = Database.connectDb();
        roomsIni.clear();
        roomService.getAllRoom(roomsIni, "rooms");
    }

    @FXML
    private void loginEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }
}
