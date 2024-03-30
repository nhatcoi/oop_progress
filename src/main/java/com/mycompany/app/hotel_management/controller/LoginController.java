package com.mycompany.app.hotel_management.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private StackPane stack_form;
    @FXML
    private AnchorPane main_form;
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


    // db tools
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void login() {}
    public void signup() {}
    public void forgot() {}


    public void exit() {
        System.exit(0);
    }
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    // switch form
    public void switchForm(ActionEvent event) {
        if (event.getSource() == this.createAccount) {
            this.main_form.setVisible(false);
            this.forgot_form.setVisible(false);
            this.signup_form.setVisible(true);
        } else if (event.getSource() == this.forgotPassword) {
            this.main_form.setVisible(false);
            this.signup_form.setVisible(false);
            this.forgot_form.setVisible(true);
        } else if (event.getSource() == this.signup_login) {
            this.signup_form.setVisible(false);
            this.forgot_form.setVisible(false);
            this.main_form.setVisible(true);
        } else if (event.getSource() == this.signup_forgot) {
            this.signup_form.setVisible(false);
            this.main_form.setVisible(false);
            this.forgot_form.setVisible(true);
        } else if (event.getSource() == this.forgot_login) {
            this.forgot_form.setVisible(false);
            this.signup_form.setVisible(false);
            this.main_form.setVisible(true);
        } else if (event.getSource() == this.forgot_signup) {
            this.forgot_form.setVisible(false);
            this.main_form.setVisible(false);
            this.signup_form.setVisible(true);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

