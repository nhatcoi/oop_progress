package com.mycompany.app.hotel_management.intefaces;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    // read image
    @Override
    public ObservableList<Image> getImage(Connection connect, ObservableList<Room> roomList, ObservableList<Image> images) throws SQLException {
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
                image = new Image("file:emptyImg.png");
            }
            images.add(image);
        }

        return images;
    }
}
