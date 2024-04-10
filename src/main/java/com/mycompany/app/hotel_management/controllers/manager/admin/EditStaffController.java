package com.mycompany.app.hotel_management.controllers.manager.admin;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.entities.Staff;
import com.mycompany.app.hotel_management.entities.User;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.repositories.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditStaffController {

    public TableView<Staff> tableView;
    public TextField usernameField;
    public TextField nameField;
    public TextField positionField;
    public TextField salaryField;
    private Connection connect;
    private final ObservableList<Staff> staff = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        // how to get all staff from user table has foregion key ID staff from database => dùng join nha bé :3
        tableView.setItems(staff);

        fetchData();
    }

    void fetchData() throws SQLException {
        staff.clear();
        connect = database.connectDb();
        String sql = "SELECT staff.*,users.username FROM staff LEFT JOIN users ON users.id = staff.user_id WHERE users.role = " + UserRole.STAFF.getValue();
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Staff staff = new Staff();
            staff.setId(resultSet.getInt("id"));
            staff.setName(resultSet.getString("name"));
            staff.setPosition(resultSet.getString("position"));
            staff.setSalary(resultSet.getDouble("salary"));
            staff.setUsername(resultSet.getString("username"));
            this.staff.add(staff);
        }


    }

    public void clearInput(ActionEvent actionEvent) {

    }

    public void openSelectFile(ActionEvent actionEvent) {
    }

    public void addData(ActionEvent actionEvent) {
        connect = database.connectDb();


        String name = nameField.getText();
        String position = positionField.getText();
        double salary = Double.parseDouble(salaryField.getText());
        String username = usernameField.getText();

        String sqlGetUserId = "SELECT id FROM users WHERE username = '" + username + "'";

        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetUserId);
            resultSet.next();
            int userId = resultSet.getInt("id");

            String sql = "INSERT INTO staff (name, position, salary, user_id) VALUES ('" + name + "', '" + position + "', " + salary + ", " + userId + ")";
            statement.executeUpdate(sql);
            fetchData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void searchData(ActionEvent actionEvent) {
    }

    public void deleteData(ActionEvent actionEvent) {
        connect = database.connectDb();
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        String sql = "DELETE FROM staff WHERE id = " + selectedStaff.getId();
        try {
            Statement statement = connect.createStatement();
            statement.executeUpdate(sql);
            fetchData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void changeData(ActionEvent actionEvent) {
        connect = database.connectDb();
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        String name = nameField.getText();
        String position = positionField.getText();
        double salary = Double.parseDouble(salaryField.getText());
        String username = usernameField.getText();

        String sqlGetUserId = "SELECT id FROM users WHERE username = '" + username + "'";
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetUserId);
            resultSet.next();
            int userId = resultSet.getInt("id");

            String sql = "UPDATE staff SET name = '" + name + "', position = '" + position + "', salary = " + salary + ", user_id = " + userId + " WHERE id = " + selectedStaff.getId();
            statement.executeUpdate(sql);
            fetchData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
