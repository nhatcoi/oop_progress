package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.utils.ToolFXML;
import com.mycompany.app.week3.code1.Transmogrify;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
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
    private AnchorPane payment;
    @FXML
    private AnchorPane favorite;

    public void initialize() {
        //show(home);
    }

    public void search(ActionEvent actionEvent) {
        show(findRoom);
    }

    public void favorite(ActionEvent actionEvent) {
        show(favorite);
    }

    public void home(ActionEvent actionEvent) {
        show(home);
    }

    public void cartBooking(ActionEvent actionEvent) {
        show(payment);
    }

    public void userAccount(ActionEvent actionEvent) throws IOException {
        show(editProfile);
        signoutpane.setVisible(true);
    }

    void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(home, payment, favorite, findRoom, editProfile, signoutpane);
        for (AnchorPane pane : allPanes) {
            if (pane != paneToShow) {
                pane.setVisible(false);
            } else {
                pane.setVisible(true);
            }
        }
    }

    public void signOut() throws IOException {
        ToolFXML.openFXML("views/authForm.fxml");
        ToolFXML.closeFXML(guestPane);
    }
}
