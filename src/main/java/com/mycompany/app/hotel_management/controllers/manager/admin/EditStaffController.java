package com.mycompany.app.hotel_management.controllers.manager.admin;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Connection;

public class EditStaffController {

    private Connection connect;
    private ObservableList<User> users = FXCollections.observableArrayList();

    public void initialize() {
        // how to get all staff from user table has foregion key ID staff from database

    }

    public void clearInput(ActionEvent actionEvent) {

    }

    public void openSelectFile(ActionEvent actionEvent) {
    }

    public void addData(ActionEvent actionEvent) {
    }

    public void searchData(ActionEvent actionEvent) {
    }

    public void deleteData(ActionEvent actionEvent) {
    }

    public void changeData(ActionEvent actionEvent) {
    }

}
