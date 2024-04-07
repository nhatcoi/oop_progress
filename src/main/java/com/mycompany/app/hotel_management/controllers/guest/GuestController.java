package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GuestController {
    public Button btnEdit;
    public Button btnControl;
    public Button btnRoom;
    public AnchorPane guestFormPane;
    public AnchorPane control;
    public AnchorPane status;
    public AnchorPane editScreen;
    public AnchorPane paymentScreen;
    public AnchorPane roomScreen;
    public AnchorPane editProfileScrenn;

    public void initialize() {

    }

    private void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(paymentScreen,roomScreen,editProfileScrenn);
        for (AnchorPane pane : allPanes) {
            if (pane != paneToShow) {
                pane.setVisible(false);
            } else {
                pane.setVisible(true);
            }
        }
    }


    public void payment(ActionEvent actionEvent) {
        show(paymentScreen);
    }

    public void edit(ActionEvent actionEvent) {
        show(editProfileScrenn);
    }

    public void room(ActionEvent actionEvent) {
        show(roomScreen);
    }

    public void control(ActionEvent actionEvent) {
        show(control);
    }

    public void addToPayments(ActionEvent actionEvent) {
    }
}
