package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.entities.User;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.repositories.database;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.mycompany.app.hotel_management.utils.Dialog;

public class HomeController {
    @FXML
    public Button btnOut;
    public Label lbName;
    public Button btnRoom;
    public Button btnEditStaff;
    public Button btnSearch;
    public TextField searchField;
    @FXML
    private AnchorPane paneHome;
    @FXML
    private AnchorPane control;
    @FXML
    private AnchorPane edit;
    @FXML
    private AnchorPane status;
    @FXML
    private AnchorPane guests;
    @FXML
    private Button btnControl;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnStatus;
    @FXML
    private Button btnGuest;
    @FXML
    private Button btnUs;
    public static User user;
    public void initialize() throws SQLException {
        lbName.setText("Xin Chào, " + HomeController.user.getUsername() + "!");
        if(HomeController.user.getRole() == UserRole.GUEST.getValue()) {
            btnControl.setVisible(false);
            btnEdit.setVisible(false);
            btnStatus.setVisible(false);
            btnGuest.setVisible(false);
            btnUs.setVisible(false);
            btnEditStaff.setVisible(false);
            control.setVisible(false);
        }else if(HomeController.user.getRole() == UserRole.STAFF.getValue()) {
            btnEditStaff.setVisible(false);
        }
    }

    @FXML
    void signOut() throws IOException {
        ToolFXML.openFXML("views/authForm.fxml", 600, 400);
        ToolFXML.closeFXML(paneHome);
    }

    @FXML
    void control(ActionEvent event) {
        if (event.getSource() == this.btnControl) {
            show(control);
        }
    }

    @FXML
    void edit(ActionEvent event) {
        if (event.getSource() == this.btnEdit) {
            show(edit);
        }
    }

    @FXML
    void guests(ActionEvent event) {
        if (event.getSource() == this.btnGuest) {
            show(guests);
        }
    }

    @FXML
    void status(ActionEvent event) {
        if (event.getSource() == this.btnStatus) {
            show(status);
        }
    }

    @FXML
    void aboutUs(ActionEvent event) {
        if (event.getSource() == this.btnUs) {
            try {
                // open link browser
                URI uri = new URI("https://github.com/nhatcoi/project_java");
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                } else {
                    System.out.println("Desktop browsing is not supported on this platform.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(control, edit, guests, status);
        for (AnchorPane pane : allPanes) {
            if (pane != paneToShow) {
                pane.setVisible(false);
            } else {
                pane.setVisible(true);
            }
        }
    }



}
