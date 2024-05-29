package com.mycompany.app.hotel_management.service;

import com.mycompany.app.hotel_management.entities.Staff;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface StaffService {
    void fetchStaff(ObservableList<Staff> staffs) throws SQLException;
    void addStaff(Staff staff);
    void deleteStaff(Staff staff);
    void updateStaff(Staff staff);
}
