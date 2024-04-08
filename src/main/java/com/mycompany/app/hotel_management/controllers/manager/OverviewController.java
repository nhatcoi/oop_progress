package com.mycompany.app.hotel_management.controllers.manager;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.intefaces.RoomServiceImpl;
import com.mycompany.app.hotel_management.repositories.database;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OverviewController {
    @FXML
    private AnchorPane overview;
    @FXML
    private Label lbAvailable;

    @FXML
    private Label lbTotalIncome;

    @FXML
    private Label lbRented;

    @FXML
    private ImageView image1;

    @FXML
    private AnchorPane slidePane;
    @FXML
    private Button btnChangeImage;


    RoomServiceImpl roomService = new RoomServiceImpl();
    public int currentImageIndex = 0;

    ObservableList<Image> images;
    ObservableList<Room> roomList;


    public void initialize() throws SQLException {
        // Lấy danh sách phòng từ database
        this.roomList = roomService.getAllRoom();
        // Lấy danh sách ảnh từ database
        this.images = roomService.getImage();


        lbAvailable.setText(String.valueOf(this.roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.AVAILABLE.getText())).count()));

        lbRented.setText(String.valueOf(this.roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.OCCUPIED.getText())).count()));
        // Tổng tổng tiền của các phòng đã cho thuê
        lbTotalIncome.setText(String.valueOf(this.roomList.stream().filter(room -> room.getStatus().equals(RoomStatus.OCCUPIED.getText())).mapToDouble(Room::getPrice).sum()));

    }




    public void changeImage(ActionEvent actionEvent) throws SQLException {

        if (!images.isEmpty()) {

            image1.setImage(images.get(currentImageIndex));

            currentImageIndex = (currentImageIndex + 1) % images.size();
        }
    }


//    void fetchImageFromDb(){
//        // Lấy danh sách các phòng có ảnh
//        // java stream api
//        List<Integer> roomIds = roomList.stream().map(Room::getId).collect(Collectors.toList());
//
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
}
