package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import com.mycompany.app.week3.code1.Transmogrify;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class GuestController {
    public AnchorPane guestPane;
    public AnchorPane navigateBar;
    public AnchorPane signoutpane;
    public StackPane guestStackPane;
    @FXML
    private AnchorPane editProfile;

    @FXML
    private AnchorPane findRoom;
    @FXML
    private AnchorPane home;
    @FXML
    protected AnchorPane payment;
    @FXML
    private AnchorPane favorite;
    public static Guest guest = new Guest();


    public void initialize() throws IOException, SQLException {
        //show(home);
        System.out.println(guest.toString());
    }

    public void search() {
        show(findRoom);
    }

    public void favorite() {
        show(favorite);
    }

    public void home() {
        show(home);
    }

    public void cartBooking() {
        show(payment);
    }

    public void userAccount() throws IOException {
        show(editProfile);
        signoutpane.setVisible(true);
    }

    public void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(home, payment, favorite, findRoom, editProfile, signoutpane);
        for (AnchorPane pane : allPanes) {
            pane.setVisible(pane == paneToShow);
        }
    }

    public void signOut() throws IOException {
        ToolFXML.openFXML("views/authForm.fxml");
        ToolFXML.closeFXML(guestPane);
    }
}
