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
    private AnchorPane guestPane, signOutPane, editProfile, findRoom, home, payment, detail;

    public static Guest guest = new Guest();

    @FXML
    public void initialize() throws SQLException {
        showPane(home);
    }

    @FXML
    public void search() {
        showPane(findRoom);
    }

    @FXML
    public void detail() {
        showPane(detail);
    }

    @FXML
    public void home() {
        showPane(home);
    }

    @FXML
    public void cartBooking() {
        showPane(payment);
    }

    @FXML
    public void userAccount() {
        showPane(editProfile);
        signOutPane.setVisible(true);
    }

    @FXML
    public void signOut() throws IOException, SQLException, ParseException {
        guest = new Guest();
        user = new User();
        super.initialize();
        ToolFXML.openFXML("views/authForm.fxml", 600, 400);
        ToolFXML.closeFXML(guestPane);
    }

    private void showPane(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(home, payment, detail, findRoom, editProfile, signOutPane);
        for (AnchorPane pane : allPanes) {
            pane.setVisible(pane == paneToShow);
        }
    }
}
