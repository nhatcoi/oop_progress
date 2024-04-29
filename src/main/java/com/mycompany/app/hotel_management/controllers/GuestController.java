package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.entities.User;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static com.mycompany.app.hotel_management.controllers.ManagerController.user;

public class GuestController extends AuthController {
    @FXML
    private  AnchorPane guestPane;
    @FXML
    private  AnchorPane signOutPane;
    @FXML
    private  AnchorPane editProfile;
    @FXML
    private AnchorPane findRoom;
    @FXML
    private  AnchorPane home;
    @FXML
    private  AnchorPane payment;
    @FXML
    private  AnchorPane detail;


    public static Guest guest = new Guest();

    public void initialize() throws SQLException {
        show(home);
    }

    public void search() {
        show(findRoom);
    }

    public void detail() {
        show(detail);
    }

    public void home() {
        show(home);
    }

    public void cartBooking() {
        show(payment);
    }

    public void userAccount() throws IOException {
        show(editProfile);
        signOutPane.setVisible(true);
    }

    public void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(home, payment, detail, findRoom, editProfile, signOutPane);
        for (AnchorPane pane : allPanes) {
            pane.setVisible(pane == paneToShow);
        }
    }

    public void signOut() throws IOException, SQLException, ParseException {
        guest = new Guest();
        user = new User();
        super.initialize();
        ToolFXML.openFXML("views/authForm.fxml", 600, 400);
        ToolFXML.closeFXML(guestPane);
    }
}
