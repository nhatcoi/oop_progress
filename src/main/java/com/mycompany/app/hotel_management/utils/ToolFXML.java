package com.mycompany.app.hotel_management.utils;

import com.mycompany.app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ToolFXML {
    public static void openFXML(String open, double x , double y) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(App.class.getResource(open))));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, x, y));
        stage.show();
    }
    public static void openFXML(String open) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(App.class.getResource(open))));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void closeFXML(AnchorPane form_close) {
        Stage stage = (Stage) form_close.getScene().getWindow();
        stage.close();
    }

    public static void closeFXML(StackPane form_close) {
        Stage stage = (Stage) form_close.getScene().getWindow();
        stage.close();
    }
}
