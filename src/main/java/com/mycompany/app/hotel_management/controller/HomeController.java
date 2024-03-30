package com.mycompany.app.hotel_management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

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
