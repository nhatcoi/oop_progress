package com.mycompany.app.hotel_management.controllers.guest;


import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.controllers.ManagerController;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.mycompany.app.hotel_management.controllers.ManagerController.user;

public class EditProfileController extends GuestController {

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfEmail;

    @FXML
    private Label lbPhone;

    @FXML
    private Label lbAddress;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbUsername;

    public void initialize()  {
        super.initialize();
        if (lbUsername != null) {
            lbUsername.setText("123");
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
    void updateInfo(ActionEvent event) {

    }

    @FXML
    void out() throws IOException {
        super.signOut();
    }
}
