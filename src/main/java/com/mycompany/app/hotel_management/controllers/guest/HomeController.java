package com.mycompany.app.hotel_management.controllers.guest;

import com.mycompany.app.hotel_management.controllers.GuestController;
import com.mycompany.app.hotel_management.controllers.ManagerController;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.Dialog;
import com.mycompany.app.hotel_management.utils.ToolFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.mycompany.app.hotel_management.controllers.guest.PaymentController.roomBooking;

public class HomeController extends GuestController {

    @FXML
    private Button search;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;


    @FXML
    private Label lbPrice2;

    @FXML
    private Label lbPrice1;

    @FXML
    private Label lbName1;

    @FXML
    private Label lbName2;

    @FXML
    private Label lbName3;

    @FXML
    private TextField tfSearch;

    @FXML
    private Label lbPrice3;

    Connection connect;

    final ObservableList<Room> rooms = FXCollections.observableArrayList();
    private ObservableList<Image> images = FXCollections.observableArrayList();
    int[] id = new int[3];

    RoomServiceImpl sv = new RoomServiceImpl();

    public void initialize() throws SQLException {

        connect = Database.connectDb();
        sv.getAllRoom(connect, rooms, "rooms");
        images = sv.getImage(connect, rooms, images);

        randomRoom();
    }

    private void randomRoom() throws SQLException {
        List<Label> names = List.of(lbName1, lbName2, lbName3);
        List<Label> prices = List.of(lbPrice1, lbPrice2, lbPrice3);
        List<Room> selectedRooms = rooms.size() <= names.size() ? rooms : getRandomSublist(rooms, names.size());


        for (int i = 0; i < selectedRooms.size(); i++) {
            names.get(i).setText(selectedRooms.get(i).getName());
            prices.get(i).setText(String.valueOf(selectedRooms.get(i).getPrice()));

            id[i] = rooms.indexOf(selectedRooms.get(i));
            matchImg(id);
        }
    }

    private List<Room> getRandomSublist(List<Room> list, int size) {
        List<Room> sublist = new ArrayList<>(list);
        Collections.shuffle(sublist, new Random());
        return sublist.subList(0, size);
    }

    // choose list image match room random
    private void matchImg(int[] id) {
        List<ImageView> imageList = List.of(image1, image2, image3);
        for (int i = 0; i < 3; i++) {
            imageList.get(i).setImage(images.get(id[i]));
        }
    }

    @FXML
    void otherRoom() throws SQLException {
        randomRoom();
    }
    @FXML
    void searchRoom() throws SQLException {
        String search = tfSearch.getText();
        if (search.isEmpty()) {
            sv.getAllRoom(connect, rooms, "rooms");
        } else {
            try {
                connect = Database.connectDb();
                String sql = "SELECT * FROM rooms WHERE name LIKE '%" + search + "%'";
                assert connect != null;
                ResultSet resultSet = connect.createStatement().executeQuery(sql);
                rooms.clear();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String type = RoomType.values()[resultSet.getInt("type")].getText();
                    String status = RoomStatus.values()[resultSet.getInt("status")].getText();
                    double price = resultSet.getDouble("price");

                    rooms.add(new Room(id, name, type, status, price));
                }
                randomRoom();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void booking1() {
        booking(1);
    }

    public void booking2() {
        booking(2);
    }

    public void booking3() {
        booking(3);
    }

    private void booking(int idxTag) {
        idxTag = idxTag - 1;
        if(rooms.get(id[idxTag]).getStatus().equals(RoomStatus.OCCUPIED.getText())) {
            Dialog.showError("Room is occupied", null, "Room " + rooms.get(id[idxTag]).getName() + " is occupied, please choose another room");
            return;
        }
        if(roomBooking.contains(rooms.get(id[idxTag]))) {
            Dialog.showError("Room is already in cart", null, "Room " + rooms.get(id[idxTag]).getName() + " is already in cart, please open cart to check in room");
            return;
        }

        roomBooking.add(rooms.get(id[idxTag]));
        Dialog.showInformation("Add to Cart", null, "Add room " + rooms.get(id[idxTag]).getName() + " to payment Successfully \nCarry out payment to finish booking");
    }

}