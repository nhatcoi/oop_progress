package com.mycompany.app.hotel_management.utils;

import com.mycompany.app.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class ToolFXML {
    private static double x = 0.0;
    private static double y = 0.0;
    public static void openFXML(String open, double a , double b) throws IOException {

        Parent root = FXMLLoader.load((Objects.requireNonNull(App.class.getResource(open))));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("NVDEV - Hotel Management System");
        Scene scene = new Scene(root, a, b);
        primaryStage.setScene(scene);
        root.setOnMousePressed((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged((event) -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
            primaryStage.setOpacity(0.8);
        });
        root.setOnMouseReleased((event) -> {
            primaryStage.setOpacity(1.0);
        });
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
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
