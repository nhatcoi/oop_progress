package com.mycompany.app.hotel_management.service.Impl;

import com.mycompany.app.hotel_management.service.StaffService;
import com.mycompany.app.hotel_management.entities.Staff;
import com.mycompany.app.hotel_management.enums.UserRole;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StaffServiceImpl implements StaffService {
    Connection connect;
    public void fetchStaff(ObservableList<Staff> staffs) throws SQLException {
        staffs.clear();
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
            staffs.add(staff);
        }
    }

    // add staff
    public void addStaff(Staff staff) {
        connect = Database.connectDb();
        String sqlGetUserId = "SELECT id, role FROM users WHERE username = '" + staff.getUsername() + "'";
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetUserId);
            if(resultSet.next()) {
                int userId = resultSet.getInt("id");
                int userRole = Integer.parseInt(resultSet.getString("role"));
                String sql = "INSERT INTO staff (name, position, salary, user_id) VALUES ('" + staff.getName() + "', '" + staff.getPosition() + "', " + staff.getSalary() + ", " + userId + ")";
                String sqlUpdateRole = "UPDATE users SET role = " + UserRole.STAFF.getValue() + " WHERE id = " + userId;
                statement.executeUpdate(sql);
                if(userRole == UserRole.GUEST.getValue()) {
                    statement.executeUpdate(sqlUpdateRole);
                }
            } else {
                Dialog.showError("Error", null, "Username not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // lay off staff
    public void deleteStaff(Staff staff) {
        connect = Database.connectDb();
        String sql = "DELETE FROM staff WHERE id = " + staff.getId();
        try {
            Statement statement = connect.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // update staff
    public void updateStaff(Staff staff) {
        connect = Database.connectDb();
        String sqlGetUserId = "SELECT id FROM users WHERE username = '" + staff.getUsername() + "'";
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetUserId);
            if(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String sql = "UPDATE staff SET name = '" + staff.getName() + "', position = '" + staff.getPosition() + "', salary = " + staff.getSalary() + " WHERE id = " + staff.getId();
                statement.executeUpdate(sql);
            } else {
                Dialog.showError("Error", null, "Username not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
