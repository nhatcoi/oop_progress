package com.mycompany.app.hotel_management.controller;

import com.mycompany.app.hotel_management.util.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class HomeController {
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


    @FXML
    void signOut() throws IOException {
        ToolFXML.openFXML("login.fxml", 600, 400);
        ToolFXML.closeFXML(paneHome);
    }

    @FXML
    void control(ActionEvent event) {

        if(event.getSource() == this.btnControl) {
            show(control);
        }
    }

    @FXML
    void edit(ActionEvent event) {
        if(event.getSource() == this.btnEdit) {
            show(edit);
        }
    }

    @FXML
    void guests(ActionEvent event) {
        if(event.getSource() == this.btnGuest) {
            show(guests);
        }
    }

    @FXML
    void status(ActionEvent event) {
        if(event.getSource() == this.btnStatus) {
            show(status);
        }
    }

    @FXML
    void aboutUs(ActionEvent event) {
        if(event.getSource() == this.btnUs) {
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
