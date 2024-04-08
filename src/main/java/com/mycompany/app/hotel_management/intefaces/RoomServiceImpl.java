package com.mycompany.app.hotel_management.intefaces;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.repositories.database;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {
    Connection connect;
    @Override
    public ObservableList<Room> getAllRoom() throws SQLException {
//        List<User> users = userRepository.findAll();
//        if (users.isEmpty()) {
//            System.out.println("Danh sách trống!");
//        }
//        return users;

        ObservableList<Room> roomList = FXCollections.observableArrayList();

        connect = database.connectDb();
        String query = "SELECT * FROM rooms";
        assert connect != null;
        ResultSet resultSet = connect.createStatement().executeQuery(query);
        roomList.clear();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String type = RoomType.values()[resultSet.getInt("type")].getText();
            String status = RoomStatus.values()[resultSet.getInt("status")].getText();
            double price = resultSet.getDouble("price");

            roomList.add(new Room(id, name, type, status, price));
        }
        return roomList;
    }

    // get Image from database
    @Override
    public ObservableList<Image> getImage() throws SQLException {
            // Lấy danh sách các phòng có ảnh
            // java stream api
            ObservableList<Room> roomList = getAllRoom();
            ObservableList<Image> images = FXCollections.observableArrayList();
        List<Integer> roomIds = roomList.stream().map(Room::getId).collect(Collectors.toList());

        try {
            String query = "SELECT * FROM pictures WHERE room_id IN (";
            for (int i = 0; i < roomIds.size(); i++) {
                query += "?"; // Thêm tham số "?"
                if (i < roomIds.size() - 1) {
                    query += ", "; // Thêm dấu phẩy sau mỗi tham số, trừ tham số cuối cùng
                }
            }
            query += ")";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            for (int i = 0; i < roomList.size(); i++) {
                preparedStatement.setInt(i + 1, roomIds.get(i)); // Gán giá trị cho tham số
            }
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String base64 = rs.getString("base64");
                Image image = imgTool.base64ToImage(base64);
                images.add(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }
}
