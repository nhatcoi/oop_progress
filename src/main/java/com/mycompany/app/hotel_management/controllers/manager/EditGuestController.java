package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Guest;
import com.mycompany.app.hotel_management.intefaces.GuestServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditGuestController {
    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TableView<Guest> tableViewUser;

    @FXML
    private AnchorPane guests;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfName;
    private Connection connect;
    private final ObservableList<Guest> guestsList = FXCollections.observableArrayList();
    GuestServiceImpl sv = new GuestServiceImpl();
    public void initialize() throws SQLException {
        // code display username of staff from db
        // add display data to the table
        tableViewUser.setItems(guestsList);


        // Set up the columns in the table
        tableViewUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
                if(guest != null) {
                    tfName.setText(guest.getName());
                    tfPhoneNumber.setText(guest.getPhone());
                    tfAddress.setText(guest.getAddress());
                }
            }
        });

        sv.getData(connect, guestsList);
    }

//    public void fetchData(){
//        try {
//            connect = Database.connectDb();
//            assert connect != null;
//            ResultSet rs = connect.createStatement().executeQuery("SELECT guests.*,users.username FROM guests LEFT JOIN users ON users.id = guests.user_id");
//            while (rs.next()) {
//                guestsList.add(new Guest(rs.getInt("id"),rs.getString("username"), rs.getString("name"), rs.getString("phone"), rs.getString("address"),rs.getInt("user_id")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void removeData(ActionEvent actionEvent) {
        Guest guest = tableViewUser.getSelectionModel().getSelectedItem();
        if(guest != null) {
            connect = Database.connectDb();
            assert connect != null;
            sv.deleteData(connect, guestsList, guest);
        }
    }

    public void changeData() {

    }


   public void addData(ActionEvent actionEvent) {
//        String userName = tfUserName.getText();
//        String name = tfName.getText();
//        String phone = tfPhoneNumber.getText();
//        String address = tfAddress.getText();
//
//        String sql = "SELECT id FROM users WHERE username = '" + userName + "'";
//        try {
//            ResultSet rs = connect.createStatement().executeQuery(sql);
//            if (rs.next()) {
//                int user_id = rs.getInt("id");
//
//                String findGuest = "SELECT * FROM guests WHERE user_id = " + user_id;
//                ResultSet rsGuest = connect.createStatement().executeQuery(findGuest);
//                if (rsGuest.next()) {
//                    Dialog.showError("Guest already exists", "This user already has a guest", "Please choose another user");
//                    return;
//                }
//                String insertSql = "INSERT INTO guests (name, phone, address,user_id) VALUES (?, ?, ?, ?)";
//                PreparedStatement preparedStatement = connect.prepareStatement(insertSql);
//                preparedStatement.setString(1, name);
//                preparedStatement.setString(2, phone);
//                preparedStatement.setString(3, address);
//                preparedStatement.setInt(4, user_id);
//                preparedStatement.executeUpdate();
//
//                guestsList.clear();
//                fetchData();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
   }
}
