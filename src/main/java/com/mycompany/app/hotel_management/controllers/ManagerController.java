package com.mycompany.app.hotel_management.controllers;

import com.mycompany.app.hotel_management.entities.User;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class ManagerController {
    public AnchorPane slidePane;
    public ImageView image1;
    //    public ImageView image2;
//    public ImageView image3;
//    public AnchorPane slidePane;
//    public AnchorPane navigateBar;
    public AnchorPane paneHome;
    public AnchorPane showRoomDetails;
    public AnchorPane overview;
    public AnchorPane edit;
    public AnchorPane staff;
    public AnchorPane guests;

    public Label lbName;
    @FXML
    public Button btnOut;
    @FXML
    private Button btnRoom;
    @FXML
    private Button btnEditStaff;
    @FXML
    private Button btnControl;
    @FXML
    private Button btnManageRoom;
    @FXML
    private Button btnGuest;
    @FXML
    private Button btnUs;

    public static User user;
    public void initialize() {
        // delete 'edit staff' function if user is staff
        if (user != null) {
            lbName.setText(user.getUsername());

            // phân quyền // cả font and back
            btnEditStaff.setVisible(user.getRole() == UserRole.ADMIN.getValue());
        }
        show(overview);
    }

    @FXML
    void signOut() throws IOException {
        ToolFXML.openFXML("views/authForm.fxml", 600, 400);
        ToolFXML.closeFXML(paneHome);
    }

    @FXML
    void showRoom(ActionEvent actionEvent) {
        show(showRoomDetails);
    }
    @FXML
    void overview(ActionEvent event) {
        show(overview);
    }

    @FXML
    void manageRoom(ActionEvent event) {
        show(edit);
    }

    @FXML
    void manageGuest(ActionEvent event) {
        show(guests);
    }
    @FXML
    void manageStaff(ActionEvent actionEvent) {
        show(staff);
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


    void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(overview, edit, guests, staff, showRoomDetails);
        for (AnchorPane pane : allPanes) {
            if (pane != paneToShow) {
                pane.setVisible(false);
            } else {
                pane.setVisible(true);
            }
        }
    }
}