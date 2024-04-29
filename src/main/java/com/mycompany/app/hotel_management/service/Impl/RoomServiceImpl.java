package com.mycompany.app.hotel_management.service.Impl;


import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.service.RoomService;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoomServiceImpl extends Database implements RoomService {

    // read room
    @Override
    public void getAllRoom(Connection connect, ObservableList<Room> roomList, String table) throws SQLException {
        findAll(connect, roomList, table);
    }

    public void findAll(Connection connect, ObservableList<Room> roomList, String table) throws SQLException {
        connect = connectDb();
        String query = "SELECT * FROM " + table;

        try {
            assert connect != null;
            try (Statement statement = connect.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                roomList.clear();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String type = RoomType.values()[resultSet.getInt("type")].getText();
                    String status = RoomStatus.values()[resultSet.getInt("status")].getText();
                    double price = resultSet.getDouble("price");

                    roomList.add(new Room(id, name, type, status, price));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // read image
    @Override
    public ObservableList<Image> getImage(Connection connect, ObservableList<Room> roomList, ObservableList<Image> images) throws SQLException {
        List<Integer> roomIds = roomList.stream().map(Room::getId).collect(Collectors.toList());

        connect = Database.connectDb();
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

            for (int i = 0; i < roomList.size(); i++) {
                if(rs.next()) {
                    String base64 = rs.getString("base64");
                    Image image = imgTool.base64ToImage(base64);
                    images.add(image);
                } else {
                    images.add(new Image("file:emptyImg.png"));
                }
            }

//            while (rs.next()) {
//                String base64 = rs.getString("base64");
//                Image image = imgTool.base64ToImage(base64);
//                images.add(image);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return images;
    }

    // read image key value room
    public ObservableList<Image> getImage(Connection connect, ObservableList<Room> roomList) throws SQLException {
        ObservableList<Image> images = FXCollections.observableArrayList();
        Map<Integer, Image> roomImages = new HashMap<>();

        List<Integer> roomIds = roomList.stream().map(Room::getId).collect(Collectors.toList());

        try {
            String query = "SELECT * FROM pictures WHERE room_id IN (";
            for (int i = 0; i < roomIds.size(); i++) {
                query += "?";
                if (i < roomIds.size() - 1) {
                    query += ", ";
                }
            }
            query += ")";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            for (int i = 0; i < roomList.size(); i++) {
                preparedStatement.setInt(i + 1, roomIds.get(i));
            }
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                String base64 = rs.getString("base64");
                Image image = imgTool.base64ToImage(base64);
                roomImages.put(roomId, image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Thêm hình ảnh vào danh sách theo thứ tự phòng
        for (Room room : roomList) {
            Image image = roomImages.get(room.getId());
            if (image == null) {
                // Nếu không có hình ảnh cho phòng này, thêm hình ảnh mặc định
                image = new Image("https://via.placeholder.com/150");
            }
            images.add(image);
        }

        return images;
    }


    // add room
    public ObservableList<Room> addRoom(Connection connect, ObservableList<Room> roomList, Room room) throws SQLException {
        String sql = "INSERT INTO rooms (name, type, status, price) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, room.getName());
        preparedStatement.setInt(2, Integer.parseInt(room.getType()));
        preparedStatement.setInt(3, Integer.parseInt(room.getStatus()));
        preparedStatement.setDouble(4, room.getPrice());
        preparedStatement.executeUpdate();

        return roomList;
    }

    // update room
    public ObservableList<Room> updateRoom(Connection connect, ObservableList<Room> roomList, Room room) throws SQLException {
        String sql = "UPDATE rooms SET name = ?, type = ?, status = ?, price = ? WHERE id = ?";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, room.getName());
        preparedStatement.setInt(2, Integer.parseInt(room.getType()));
        preparedStatement.setInt(3, Integer.parseInt(room.getStatus()));
        preparedStatement.setDouble(4, room.getPrice());
        preparedStatement.setInt(5, room.getId());
        preparedStatement.executeUpdate();

        return roomList;
    }

    // delete room
    public ObservableList<Room> deleteRoom(Connection connect, ObservableList<Room> roomList, Room room) throws SQLException {
        String sql = "DELETE FROM rooms WHERE id = ?";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, room.getId());
        preparedStatement.executeUpdate();

        return roomList;
    }

    // search room
    public void searchRoom(Connection connect, ObservableList<Room> roomList, String search) throws SQLException {
        connect = Database.connectDb();
        String sql = "SELECT * FROM rooms WHERE name LIKE '%" + search + "%'";
        ResultSet resultSet = connect.createStatement().executeQuery(sql);
        roomList.clear();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String type = RoomType.values()[resultSet.getInt("type")].getText();
            String status = RoomStatus.values()[resultSet.getInt("status")].getText();
            double price = resultSet.getDouble("price");

            roomList.add(new Room(id, name, type, status, price));
        }
    }
}
