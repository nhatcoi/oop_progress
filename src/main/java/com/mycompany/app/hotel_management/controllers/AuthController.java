package com.mycompany.app.hotel_management.controllers;


import com.mycompany.app.hotel_management.entities.User;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.Md5;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    private StackPane authForm;
    @FXML
    private AnchorPane login_form;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Hyperlink forgotPassword;
    @FXML
    private Hyperlink createAccount;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private TextField signup_username;
    @FXML
    private TextField signup_email;
    @FXML
    private PasswordField signup_password;
    @FXML
    private PasswordField signup_password2;
    @FXML
    private Button signupBtn;
    @FXML
    private Hyperlink signup_forgot;
    @FXML
    private Hyperlink signup_login;
    // forgot
    @FXML
    private AnchorPane forgot_form;
    @FXML
    private TextField forgot_email;
    @FXML
    private Hyperlink forgot_login;
    @FXML
    private Hyperlink forgot_signup;

    @FXML
    private ComboBox comboBox;
    @FXML
    ProgressIndicator progress;

    // db tools
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void login() {

        connect = Database.connectDb();
        try {
            // add user to list
            String userLog = username.getText();
            String passLog = password.getText();

            if (userLog.isEmpty() || passLog.isEmpty()) {
                Dialog.showError("Đừng để trống", null, "Vui lòng nhập tên đăng nhập và mật khẩu");
            } else {

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

                    Dialog.showInformation("Đăng nhập thành công", null, "Chào mừng " + userLog);
                    if (ManagerController.user.getRole() == UserRole.GUEST.getValue()) {
                        String sql2 = "SELECT * FROM guests WHERE user_id = ?";
                        prepare = connect.prepareStatement(sql2);
                        prepare.setInt(1, ManagerController.user.getId());
                        result = prepare.executeQuery();
                        if (result.next()) {
                            GuestController.guest.setName(result.getString("name"));
                            GuestController.guest.setPhone(result.getString("phone"));
                            GuestController.guest.setAddress(result.getString("address"));
                        }
                        ToolFXML.openFXML("views/guestForm.fxml");
                    } else if (ManagerController.user.getRole() == UserRole.STAFF.getValue()) {
                        ToolFXML.openFXML("views/manager.fxml");
                    } else if (ManagerController.user.getRole() == UserRole.ADMIN.getValue()) {
                        ToolFXML.openFXML("views/manager.fxml");
                    }
                    ToolFXML.closeFXML(authForm);
                } else {
                    Dialog.showError("Đăng nhập thất bại", null, "Tên đăng nhập hoặc mật khẩu không đúng");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // sign up
    public void signup() {
        // connect to db
        connect = Database.connectDb();

        try {
            // get data from input fields
            String username = signup_username.getText();
            String password = signup_password.getText();
            String confirmPassword = signup_password2.getText();

            // check empty fields
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Dialog.showError("Đừng để trống", null, "Vui lòng điền đầy đủ thông tin");
                return; // Exit the method as input validation failed
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                Dialog.showError("Mật khẩu không khớp", null, "Mật khẩu và xác nhận mật khẩu không khớp");
                return;
            }
            password = Md5.hashString(password);

            // Check if the email or username is already registered
            String checkSql = "SELECT * FROM users WHERE username = ?";
            prepare = connect.prepareStatement(checkSql);
            prepare.setString(1, username);
            ResultSet checkResult = prepare.executeQuery();

            if (checkResult.next()) {
                Dialog.showError("Đăng ký thất bại", null, "Email hoặc tên đăng nhập đã tồn tại");
                return;
            }

            // If everything is fine, proceed with the signup
//            String insertSql = "INSERT INTO users ( username, password) VALUES (?, ?)";
//            prepare = connect.prepareStatement(insertSql);
//            prepare.setString(1, username);
//            prepare.setString(2, password);
//            prepare.executeUpdate();

            // Thêm người dùng vào bảng users
            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
            prepare = connect.prepareStatement(insertSql);
            prepare.setString(1, username);
            prepare.setString(2, password);
            prepare.executeUpdate();


            String selectIdSql = "SELECT id FROM users WHERE username = ?";
            prepare = connect.prepareStatement(selectIdSql);
            prepare.setString(1, username);
            ResultSet idResult = prepare.executeQuery();

            int userId = -1; // Default value if no ID is found
            if (idResult.next()) {
                userId = idResult.getInt("id");
            }

// Now you have the ID of the newly inserted user in the userId variable
//            System.out.println("The ID of the newly created user is: " + userId);
//
//            String insertGuestSql = "INSERT INTO guests (user_id) VALUES (?)";
//            prepare = connect.prepareStatement(insertGuestSql);
//            prepare.setInt(1, userId);
//            prepare.executeUpdate();


            Dialog.showInformation("Đăng ký thành công", null, "Tài khoản của bạn đã được tạo thành công");

            this.signup_form.setVisible(false);
            this.login_form.setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // forgot password
    public void forgot() {
//        String sql = "SELECT * FROM admin WHERE email = ?";
//        connect = database.connectDb();
//        try {
//            prepare = connect.prepareStatement(sql);
//            prepare.setString(1, forgot_email.getText());
//            result = prepare.executeQuery();
//
//            if(forgot_email.getText().isEmpty()) {
//                Dialog.showError("Đừng để trống", null, "Vui lòng nhập email");
//            } else {
//                // check if email is exist
//                if(result.next()) Dialog.showInformation("Kiểm tra email", null, "Một email đã được gửi đến " + forgot_email.getText() + " với mật khẩu của bạn");
//                else Dialog.showError("Email không tồn tại", null, "Email không tồn tại trong hệ thống");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }


    // exit and minimize
    public void exit() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) authForm.getScene().getWindow();
        stage.setIconified(true);
    }

    // switch form
    public void switchForm(ActionEvent event) {
        this.login_form.setVisible(false);
        this.signup_form.setVisible(false);
        this.forgot_form.setVisible(false);

        if (event.getSource() == this.createAccount || event.getSource() == this.signup_login || event.getSource() == this.forgot_signup) {
            this.signup_form.setVisible(true);
        } else if (event.getSource() == this.forgotPassword || event.getSource() == this.forgot_login || event.getSource() == this.signup_forgot) {
            this.forgot_form.setVisible(true);
        } else {
            this.login_form.setVisible(true);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            login();
    }
}

