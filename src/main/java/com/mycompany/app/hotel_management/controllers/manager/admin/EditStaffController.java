package com.mycompany.app.hotel_management.controllers.manager.admin;

import com.mycompany.app.hotel_management.service.Impl.StaffServiceImpl;
import com.mycompany.app.hotel_management.entities.Staff;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
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
    private final ObservableList<Staff> staffs = FXCollections.observableArrayList();

    protected static int idStaff;
    StaffServiceImpl sv = new StaffServiceImpl();
    public void initialize() throws SQLException {
        cbRaise.getItems().addAll("Default", "5%", "10%", "20%", "30%");

        // how to get all staff from user table has foreign key ID staff from database => dùng join nha bé :3 ==> dạ
        tableView.setItems(staffs);
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
                if(event.getClickCount() == 2 && selectedStaff != null) {
                    idStaff = selectedStaff.getId();
                    try {
                        ToolFXML.openFXML( "views/managerDetails/admin/StaffProfile.fxml", 386, 551);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        });
    }

    public void fetchData() throws SQLException {
        sv.fetchStaff(staffs);
    }

    public void clearInput() {
        nameField.clear();
        positionField.clear();
        salaryField.clear();
        usernameField.clear();
    }

    public void addData() throws SQLException {
        connect = Database.connectDb();
        String username = usernameField.getText();
        String name = nameField.getText();
        String position = positionField.getText();
        double salary = Double.parseDouble(salaryField.getText());

        Staff staff = new Staff();
        staff.setUsername(username);
        staff.setName(name);
        staff.setPosition(position);
        staff.setSalary(salary);

        sv.addStaff(staff);
        fetchData();
    }

    public void searchData() {
        String search = searchField.getText();
        String sql = "SELECT staff.*,users.username FROM staff LEFT JOIN users ON users.id = staff.user_id WHERE users.role = " + UserRole.STAFF.getValue() + " AND staff.name LIKE '%" + search + "%'";
        try {
            staffs.clear();
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
                this.staffs.add(staff);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteData() throws SQLException {
        connect = Database.connectDb();
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if(selectedStaff == null) {
            Dialog.showError("Error", null, "Please select a staff to delete");
            return;
        }
        sv.deleteStaff(selectedStaff);
        fetchData();
    }

    public void changeData() throws SQLException {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if(selectedStaff == null) {
            Dialog.showError("Error", null, "Please select a staff to update");
            return;
        }
        selectedStaff.setUsername(usernameField.getText());
        selectedStaff.setName(nameField.getText());
        selectedStaff.setPosition(positionField.getText());
        selectedStaff.setSalary(Double.parseDouble(salaryField.getText()));

        sv.updateStaff(selectedStaff);
        fetchData();
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
