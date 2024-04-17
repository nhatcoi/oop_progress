package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import com.mycompany.app.week3.code1.Transmogrify;
import com.mycompany.app.week5to6.controllers.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.roomBooking;

public class GuestController {
    public AnchorPane guestPane;
    public AnchorPane navigateBar;
    public AnchorPane signoutpane;
    public StackPane guestStackPane;
    @FXML
    public AnchorPane editProfile;

    @FXML
    public AnchorPane findRoom;
    @FXML
    public AnchorPane home;
    @FXML
    public AnchorPane payment;
    public AnchorPane detail;
    public static Guest guest = new Guest();



    public void initialize() throws IOException, SQLException {
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
        signoutpane.setVisible(true);
    }

    public void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(home, payment, detail, findRoom, editProfile, signoutpane);
        for (AnchorPane pane : allPanes) {
            pane.setVisible(pane == paneToShow);
        }
    }

    public void signOut() throws IOException {
        ToolFXML.openFXML("views/authForm.fxml");
        ToolFXML.closeFXML(guestPane);
    }

    public void imgDetail1(ActionEvent actionEvent) {
        show(detail);
    }

    public void imgDetail2(ActionEvent actionEvent) {
        show(detail);
    }

    public void imgDetail3(ActionEvent actionEvent) {
        show(detail);
    }



}
