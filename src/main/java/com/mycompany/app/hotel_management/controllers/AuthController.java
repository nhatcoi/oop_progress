package com.mycompany.app.hotel_management.controllers;


import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.entities.User;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.*;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class AuthController {

    public TextField tfEmail;
    public TextField tfUsername;
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
                            GuestController.guest.setId(result.getInt("id"));
                            GuestController.guest.setName(result.getString("name"));
                            GuestController.guest.setPhone(result.getString("phone"));
                            GuestController.guest.setAddress(result.getString("address"));
                            GuestController.guest.setEmail(result.getString("email"));
                            GuestController.guest.setUser_id(result.getInt("user_id"));
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
                return;
            }
            if(username.length() < 6) {
                Dialog.showError("Tên đăng nhập quá ngắn", null, "Tên đăng nhập phải chứa ít nhất 6 ký tự");
                return;
            }
            if(password.length() < 6){
                Dialog.showError("Mật khẩu quá ngắn", null, "Mật khẩu phải chứa ít nhất 6 ký tự");
                return;
            }
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

            Dialog.showInformation("Đăng ký thành công", null, "Tài khoản của bạn đã được tạo thành công");

            this.signup_form.setVisible(false);
            this.login_form.setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // forgot password
    public void requestPassword() {
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        if(Validate.validateEmail(email)) {
            Dialog.showError("Error", null, "Email is invalid");
            return;
        }
        try {
            connect = Database.connectDb();
            assert connect != null;
            String sql = "SELECT * FROM guests WHERE email = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, email);
            result = prepare.executeQuery();
            if(result.next()) {
                String newPassword = generateRandomString(6);
                String hashedPassword = Md5.hashString(newPassword);

                String updateSql = "UPDATE users " +
                        "SET password = ? " +
                        "WHERE id = (SELECT id FROM users WHERE username = ?)";
                prepare = connect.prepareStatement(updateSql);
                prepare.setString(1, hashedPassword);
                prepare.setString(2, username);
                prepare.executeUpdate();

                MailSender.sendEmail(email, "New password", "Your new password is: " + newPassword);
                Dialog.showInformation("Success", null, "Your new password has been sent to your email");
            } else {
                Dialog.showError("Error", null, "Email not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        // Khởi tạo chuỗi kết quả
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // chọn kí tự ran đầm
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
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

        if (event.getSource() == this.signup_login || event.getSource() == this.forgot_login) {
            show(login_form);
        }
        if (event.getSource() == this.createAccount || event.getSource() == this.forgot_signup) {
            show(signup_form);
        }
        if (event.getSource() == this.forgotPassword || event.getSource() == this.signup_forgot) {
            show(forgot_form);
        }

    }

    public void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(login_form, signup_form, forgot_form);
        for (AnchorPane pane : allPanes) {
            pane.setVisible(pane == paneToShow);
        }
    }



    public void initialize() throws SQLException {

    }

    public void loginEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            login();
    }

}

