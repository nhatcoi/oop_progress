package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RoomDetailsController extends OverviewController {

    @FXML
    private Label lbName;

    @FXML
    private Label lbType;

    @FXML
    private Label lbPrice;

    @FXML
    private Label lbStatus;

    @FXML
    private AnchorPane slidePane1;

    @FXML
    private ImageView imageRoom;

    @FXML
    private AnchorPane showRoomDetails;

    RoomServiceImpl roomService = new RoomServiceImpl();


    public int currentImageIndex = 0;

    public void initialize() throws SQLException {
        connect = Database.connectDb();
        sv.getAllRoom(connect, roomList, "rooms");
//        fetchDataFromDatabase();
        sv.getImage(connect, roomList, images);
    }

//    public void fetchDataFromDatabase() throws SQLException {
//
//
//        String query = "SELECT * FROM rooms";
//        assert connect != null;
//        ResultSet resultSet = connect.createStatement().executeQuery(query);
//        roomList.clear();
//        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String name = resultSet.getString("name");
//            String type = RoomType.values()[resultSet.getInt("type")].getText();
//            String status = RoomStatus.values()[resultSet.getInt("status")].getText();
//            double price = resultSet.getDouble("price");
//
//            roomList.add(new Room(id, name, type, status, price));
//        }
//    }
//
//    public void fetchImage() {
//
//        List<Integer> roomIds = roomList.stream().map(Room::getId).collect(Collectors.toList());
//        try {
//            String query = "SELECT * FROM pictures WHERE room_id IN (";
//            for (int i = 0; i < roomIds.size(); i++) {
//                query += "?"; // Thêm tham số "?"
//                if (i < roomIds.size() - 1) {
//                    query += ", "; // Thêm dấu phẩy sau mỗi tham số, trừ tham số cuối cùng
//                }
//            }
//            query += ")";
//            PreparedStatement preparedStatement = connect.prepareStatement(query);
//            for (int i = 0; i < roomList.size(); i++) {
//                preparedStatement.setInt(i + 1, roomIds.get(i)); // Gán giá trị cho tham số
//            }
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                String base64 = rs.getString("base64");
//                Image image = imgTool.base64ToImage(base64);
//                images.add(image);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void switchRight(ActionEvent event) {
        switchImage(1);
    }

    @FXML
    void switchLeft(ActionEvent event) {
        switchImage(-1);
    }

    void switchImage(int i) {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex - i + images.size()) % images.size();

            imageRoom.setImage(images.get(currentImageIndex));

            lbName.setText(roomList.get(currentImageIndex).getName());
            lbType.setText(roomList.get(currentImageIndex).getType());
            lbPrice.setText(String.valueOf(roomList.get(currentImageIndex).getPrice()));
            lbStatus.setText(roomList.get(currentImageIndex).getStatus());

////            currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
//            currentImageIndex--;
//            if(currentImageIndex == 0) {
//                currentImageIndex = images.size()-1;
//        } else {
//            imageRoom.setImage(new Image("img/goku.png"));
//
//            lbName.setText(roomList.get(currentImageIndex).getName());
//            lbType.setText(roomList.get(currentImageIndex).getType());
//            lbPrice.setText(String.valueOf(roomList.get(currentImageIndex).getPrice()));
//            lbStatus.setText(roomList.get(currentImageIndex).getStatus());
        }
    }
}