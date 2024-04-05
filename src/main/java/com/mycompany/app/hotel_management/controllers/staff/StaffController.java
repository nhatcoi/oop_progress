package com.mycompany.app.hotel_management.controllers.staff;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.List;

public class StaffController {
    public AnchorPane dashBoardScreen;
    public AnchorPane editRoomScreen;

    public void edit(ActionEvent actionEvent) {
        show(editRoomScreen);
    }

    public void dashboard(ActionEvent actionEvent) {
        show(dashBoardScreen);
    }

    private void show(AnchorPane paneToShow) {
        List<AnchorPane> allPanes = Arrays.asList(dashBoardScreen,editRoomScreen);
        for (AnchorPane pane : allPanes) {
            if (pane != paneToShow) {
                pane.setVisible(false);
            } else {
                pane.setVisible(true);
            }
        }
    }

}
