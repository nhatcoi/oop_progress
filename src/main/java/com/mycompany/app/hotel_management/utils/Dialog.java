package com.mycompany.app.hotel_management.utils;

import javafx.scene.control.Alert;

public class Dialog {
    public static void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showWarning(String title, String headerText, String contentText) {
        showAlert(Alert.AlertType.WARNING, title, headerText, contentText);
    }

    public static void showError(String title, String headerText, String contentText) {
        showAlert(Alert.AlertType.ERROR, title, headerText, contentText);
    }

    public static void showInformation(String title, String headerText, String contentText) {
        showAlert(Alert.AlertType.INFORMATION, title, headerText, contentText);
    }

    public static void showConfirmation(String title, String headerText, String contentText) {
        showAlert(Alert.AlertType.CONFIRMATION, title, headerText, contentText);
    }
}

