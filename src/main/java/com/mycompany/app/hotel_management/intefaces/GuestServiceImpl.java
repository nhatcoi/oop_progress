package com.mycompany.app.hotel_management.intefaces;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.repositories.Database;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestServiceImpl implements GuestService {

    // read
    public void getData(Connection connect, ObservableList<Guest> guestsList){
        try {
            connect = Database.connectDb();
            assert connect != null;
            ResultSet rs = connect.createStatement().executeQuery("SELECT guests.*,users.username FROM guests LEFT JOIN users ON users.id = guests.user_id");
            while (rs.next()) {
                guestsList.add(new Guest(rs.getInt("id"),rs.getString("username"), rs.getString("name"), rs.getString("phone"), rs.getString("address"),rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete
    public void deleteData(Connection connect, ObservableList<Guest> guestsList, Guest guest) {
        try {
            connect.createStatement().executeUpdate("DELETE FROM guests WHERE id = " + guest.getId());
            guestsList.remove(guest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update

}
