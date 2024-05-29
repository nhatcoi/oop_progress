package com.mycompany.app.hotel_management.controllers.manager.admin;

import com.mycompany.app.hotel_management.entities.Staff;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.service.Impl.StaffServiceImpl;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.Md5;
import com.mycompany.app.hotel_management.utils.Security;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditStaffController {

    @FXML
    private TableView<Staff> tableView;
    @FXML
    private TextField usernameField, nameField, positionField, salaryField, searchField;
    @FXML
    private ComboBox<String> cbRaise;
    private static final double OFFSET_X = 100; // Giá trị dịch sang phải
    ContextMenu contextMenu = new ContextMenu();

    private final ObservableList<Staff> staffs = FXCollections.observableArrayList();
    private final StaffServiceImpl staffService = new StaffServiceImpl();
    protected static int idStaff;

    @FXML
    public void initialize() throws SQLException {
        cbRaise.getItems().addAll("Default", "5%", "10%", "20%", "30%");
        tableView.setItems(staffs);
        fetchData();

        tableView.setOnMouseClicked(this::handleTableClick);
        addContextMenu();

    }

    private void addContextMenu() {
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> editSelectedRow());
        MenuItem resetPassword = new MenuItem("Reset Password");
        resetPassword.setOnAction(event -> showResetPasswordMenu ());
        contextMenu.getItems().addAll(editItem, resetPassword);
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }

    private void showResetPasswordMenu () {
        ContextMenu resetPasswordMenu = new ContextMenu();
        MenuItem autoPassword = new MenuItem("Auto-generate password");
        MenuItem manualPassword = new MenuItem("Manual reset password");
        autoPassword.setOnAction(event -> autoPassword());
        manualPassword.setOnAction(event -> manualPassword());
        resetPasswordMenu.getItems().addAll(autoPassword, manualPassword);
        resetPasswordMenu.show(tableView, contextMenu.getScene().getWindow().getX(), contextMenu.getScene().getWindow().getY());
    }

    private void manualPassword() {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            String username = selectedStaff.getUsername();
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            try (Connection connect = Database.connectDb()) {
                assert connect != null;
                try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                    String newPassword = Dialog.showInput("Reset Password", null, "Enter new password");
                    String hashPassword = Md5.hashString(newPassword);
                    preparedStatement.setString(1, hashPassword);
                    preparedStatement.setString(2, username);
                    preparedStatement.executeUpdate();
                    Dialog.showInformation("Success", null, "Reset password successfully");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void autoPassword() {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            String username = selectedStaff.getUsername();
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            try (Connection connect = Database.connectDb()) {
                assert connect != null;
                try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                    String newPassword = Security.generateRandomString(6);
                    String hashPassword = Md5.hashString(newPassword);
                    preparedStatement.setString(1, hashPassword);
                    preparedStatement.setString(2, username);
                    preparedStatement.executeUpdate();
                    Dialog.showInformation("Success", null, "Reset password successfully \nNew password: " + newPassword);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void editSelectedRow() {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            ContextMenu editMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Update");
            editItem.setOnAction(event -> {
                try {
                    changeData();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            MenuItem deleteItem = new MenuItem("Lay off");
            deleteItem.setOnAction(event -> {
                try {
                    deleteData();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            editMenu.getItems().addAll(editItem, deleteItem);
            editMenu.show(tableView, contextMenu.getScene().getWindow().getX(), contextMenu.getScene().getWindow().getY());
        }
    }

    private void handleTableClick(MouseEvent event) {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            nameField.setText(selectedStaff.getName());
            positionField.setText(selectedStaff.getPosition());
            salaryField.setText(String.valueOf(selectedStaff.getSalary()));
            usernameField.setText(selectedStaff.getUsername());
        }
        if (event.getClickCount() == 2 && selectedStaff != null) {
            idStaff = selectedStaff.getId();
            try {
                ToolFXML.openFXML("views/managerDetails/admin/StaffProfile.fxml", 386, 551);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void fetchData() throws SQLException {
        staffService.fetchStaff(staffs);
    }

    public void clearInput() {
        nameField.clear();
        positionField.clear();
        salaryField.clear();
        usernameField.clear();
    }

    public void addData() throws SQLException {
        String username = usernameField.getText();
        String name = nameField.getText();
        String position = positionField.getText();
        double salary = Double.parseDouble(salaryField.getText());

        Staff staff = new Staff();
        staff.setUsername(username);
        staff.setName(name);
        staff.setPosition(position);
        staff.setSalary(salary);

        staffService.addStaff(staff);
        fetchData();
    }

    public void searchData() throws SQLException {
        String search = searchField.getText();
        String sql = "SELECT staff.*, users.username FROM staff LEFT JOIN users ON users.id = staff.user_id WHERE users.role = ? AND staff.name LIKE ?";
        try (Connection connect = Database.connectDb()) {
            assert connect != null;
            try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                preparedStatement.setInt(1, UserRole.STAFF.getValue());
                preparedStatement.setString(2, "%" + search + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    staffs.clear();
                    while (resultSet.next()) {
                        Staff staff = new Staff();
                        staff.setId(resultSet.getInt("id"));
                        staff.setName(resultSet.getString("name"));
                        staff.setPosition(resultSet.getString("position"));
                        staff.setSalary(resultSet.getDouble("salary"));
                        staff.setUsername(resultSet.getString("username"));
                        staffs.add(staff);
                    }
                }
            }
        }
    }

    public void deleteData() throws SQLException {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if (selectedStaff == null) {
            Dialog.showError("Error", null, "Please select a staff to delete");
            return;
        }
        staffService.deleteStaff(selectedStaff);
        fetchData();
    }

    public void changeData() throws SQLException {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if (selectedStaff == null) {
            Dialog.showError("Error", null, "Please select a staff to update");
            return;
        }
        selectedStaff.setUsername(usernameField.getText());
        selectedStaff.setName(nameField.getText());
        selectedStaff.setPosition(positionField.getText());
        selectedStaff.setSalary(Double.parseDouble(salaryField.getText()));

        staffService.updateStaff(selectedStaff);
        fetchData();
        Dialog.showInformation("Success", null, "Update staff successfully");
    }

    public void getRaise() {
        Staff selectedStaff = tableView.getSelectionModel().getSelectedItem();
        if (selectedStaff == null) {
            Dialog.showError("Error", null, "Please select a staff to get raise");
            return;
        }
        double salary = selectedStaff.getSalary();
        double newSalary = getNewSalary(salary);
        salaryField.setText(String.valueOf(newSalary));
    }

    private double getNewSalary(double salary) {
        String raise = cbRaise.getValue();
        double raiseValue = 0;

        switch (raise) {
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
        return newSalary;
    }
}
