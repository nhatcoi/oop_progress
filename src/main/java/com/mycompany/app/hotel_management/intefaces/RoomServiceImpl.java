package com.mycompany.app.hotel_management.intefaces;

import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.repositories.Database;
import com.mycompany.app.hotel_management.utils.imgTool;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
}
