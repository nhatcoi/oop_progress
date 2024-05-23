package com.mycompany.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;



public class App extends Application {

    private double x = 0.0;
    private double y = 0.0;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(App.class.getResource("views/authForm.fxml"))));
        primaryStage.setTitle("NVDEV - Hotel Management System");
        Scene scene = new Scene(root, 592.0, 410.0);
        primaryStage.setScene(scene);
        root.setOnMousePressed((event) -> {
            this.x = event.getSceneX();
            this.y = event.getSceneY();
        });
        root.setOnMouseDragged((event) -> {
            primaryStage.setX(event.getScreenX() - this.x);
            primaryStage.setY(event.getScreenY() - this.y);
            primaryStage.setOpacity(0.8);
        });
        root.setOnMouseReleased((event) -> {
            primaryStage.setOpacity(1.0);
        });
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Group 17");
        launch(args);
    }

}