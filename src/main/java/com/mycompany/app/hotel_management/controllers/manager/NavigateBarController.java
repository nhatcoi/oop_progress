package com.mycompany.app.hotel_management.controllers.manager;


import com.mycompany.app.hotel_management.controllers.ManagerController;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class NavigateBarController extends ManagerController {
    @FXML
    private Button btnControl;

    @FXML
    private Button btnUs;

    @FXML
    private Button btnOut;

    @FXML
    private Label lbName;

    @FXML
    private Button btnManageRoom;

    @FXML
    private Button btnGuest;

    @FXML
    private AnchorPane navigateBar;

    @FXML
    private Button btnEditStaff;

    @FXML
    private Button btnRoom;

//    ManagerController mc;
//
//    public void initialize() {
//        show(control);
//    }
//
//
//    @FXML
//    void signOut() throws IOException {
//        ToolFXML.openFXML("views/authForm.fxml", 600, 400);
//        ToolFXML.closeFXML(mc.paneHome);
//    }
//
//    @FXML
//    void showRoom(ActionEvent actionEvent) {
//    }
//    @FXML
//    void control(ActionEvent event) {
//        show(mc.control);
//    }
//
//    @FXML
//    void manageRoom(ActionEvent event) {
//        show(mc.edit);
//    }
//
//    @FXML
//    void manageGuest(ActionEvent event) {
//        show(mc.guests);
//    }
//    @FXML
//    void manageStaff(ActionEvent actionEvent) {
//        show(mc.staff);
//    }


//    @FXML
//    void aboutUs(ActionEvent event) {
//        if (event.getSource() == this.btnUs) {
//            try {
//                // open link browser
//                URI uri = new URI("https://github.com/nhatcoi/project_java");
//                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
//                    Desktop.getDesktop().browse(uri);
//                } else {
//                    System.out.println("Desktop browsing is not supported on this platform.");
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
