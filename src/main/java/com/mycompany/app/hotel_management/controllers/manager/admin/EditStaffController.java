package com.mycompany.app.hotel_management.controllers.manager.admin;

import com.mycompany.app.hotel_management.entities.Staff;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
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
    public TextField searchField;
    public ComboBox<String> cbRaise;
    private Connection connect;
    private final ObservableList<Staff> staff = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        cbRaise.getItems().addAll("Default", "5%", "10%", "20%", "30%");

        // how to get all staff from user table has foreign key ID staff from database => dùng join nha bé :3 ==> dạ
        tableView.setItems(staff);
        fetchData();

        tableView.setOnMouseClicked(event -> {
            tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
                if(selectedStaff != null) {
                    nameField.setText(selectedStaff.getName());
                    positionField.setText(selectedStaff.getPosition());
                    salaryField.setText(String.valueOf(selectedStaff.getSalary()));
                    usernameField.setText(selectedStaff.getUsername());
                }
            });
        });
    }

    void fetchData() throws SQLException {
        staff.clear();
        connect = Database.connectDb();
        String sql = "SELECT staff.*,users.username FROM staff LEFT JOIN users ON users.id = staff.user_id WHERE users.role = " + UserRole.STAFF.getValue();
        assert connect != null;
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

    public void clearInput() {
        nameField.clear();
        positionField.clear();
        salaryField.clear();
        usernameField.clear();
    }

    public void addData() {
        connect = Database.connectDb();
        String name = nameField.getText();
        String position = positionField.getText();
        double salary = Double.parseDouble(salaryField.getText());
        String username = usernameField.getText();

        String sqlGetUserId = "SELECT id FROM users WHERE username = '" + username + "'";
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetUserId);
            if(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String sql = "INSERT INTO staff (name, position, salary, user_id) VALUES ('" + name + "', '" + position + "', " + salary + ", " + userId + ")";
                statement.executeUpdate(sql);
            } else {
                Dialog.showError("Error", null, "Username not found");
            }

            fetchData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void searchData() {
        String search = searchField.getText();
        String sql = "SELECT staff.*,users.username FROM staff LEFT JOIN users ON users.id = staff.user_id WHERE users.role = " + UserRole.STAFF.getValue() + " AND staff.name LIKE '%" + search + "%'";
        try {
            staff.clear();
            connect = Database.connectDb();
            assert connect != null;
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteData() {
        connect = Database.connectDb();
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

    public void changeData() {
        connect = Database.connectDb();
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if(selectedStaff == null) {
            Dialog.showError("Error", null, "Please select a staff to update");
            return;
        }
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
        Dialog.showInformation("Success", null, "Update staff successfully");
    }

    public void getRaise() {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if(selectedStaff == null) {
            Dialog.showError("Error", null, "Please select a staff to get raise");
            return;
        }
        double salary = selectedStaff.getSalary();
        String raise = cbRaise.getValue();
        double raiseValue = 0;
        switch (raise) {
            case "Default":
                break;
            case "5%":
                raiseValue = salary * 0.05;
                break;
            case "10%":
                raiseValue = salary * 0.1;
                break;
            case "20%":
                raiseValue = salary * 0.2;
                break;
            case "30%":
                raiseValue = salary * 0.3;
                break;
        }

        double newSalary = salary + raiseValue;
        salaryField.setText(String.valueOf(newSalary));
    }
}
