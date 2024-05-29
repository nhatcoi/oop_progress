package com.mycompany.app.hotel_management.service.Impl;

import com.mycompany.app.hotel_management.service.GuestService;
import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.repositories.Database;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestServiceImpl implements GuestService {

    Connection connect;
    // read
    public void getData(ObservableList<Guest> guestsList){
        try {
            connect = Database.connectDb();
            assert connect != null;
            ResultSet rs = connect.createStatement().executeQuery("SELECT guests.*,users.username FROM guests LEFT JOIN users ON users.id = guests.user_id");
            while (rs.next()) {
                guestsList.add(new Guest(rs.getInt("id"),rs.getString("username"), rs.getString("name"), rs.getString("phone"), rs.getString("address"), rs.getString("email") ,rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete
    public void deleteData(ObservableList<Guest> guestsList, Guest guest) {
        try {
            connect = Database.connectDb();
            assert connect != null;
            connect.createStatement().executeUpdate("DELETE FROM guests WHERE id = " + guest.getId());
            guestsList.remove(guest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update
    public void updateData(ObservableList<Guest> guestsList, Guest guest) {
        try {
            connect = Database.connectDb();
            assert connect != null;
            connect.createStatement().executeUpdate("UPDATE guests SET name = '" + guest.getName() + "', phone = '" + guest.getPhone() + "', address = '" + guest.getAddress() + "', email = '" + guest.getEmail() + "' WHERE id = " + guest.getId());
            guestsList.set(guestsList.indexOf(guest), guest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // create
    public void createDate(ObservableList<Guest> guestsList, Guest guest) {
        //
    }
}
