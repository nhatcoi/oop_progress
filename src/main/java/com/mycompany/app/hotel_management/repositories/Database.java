package com.mycompany.app.hotel_management.repositories;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycompany.app.hotel_management.entities.Room;
import com.mycompany.app.hotel_management.enums.RoomStatus;
import com.mycompany.app.hotel_management.enums.RoomType;
import javafx.collections.ObservableList;

import java.io.FileReader;
import java.sql.*;

public class Database {
    private static final String CONFIG_FILE = "config.json";

    public static Connection connectDb() {
        //Class.forName("com.mysql.cj.jdbc.Driver");

        //return DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_data", "root", "nhatcoi2004");

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            String host = jsonObject.get("host").getAsString();
            String databaseName = jsonObject.get("database_name").getAsString();
            String username = jsonObject.get("username").getAsString();
            String password = jsonObject.get("password").getAsString();
            // Create the connection URL
            String url = "jdbc:mysql://" + host + ":3306/" + databaseName;

            // Establish the connection
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void findAll(Connection connect, ObservableList<Room> roomList, String table) throws SQLException {
        long startTime = System.nanoTime();

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


        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Initialization time: " + elapsedTime + " nanoseconds");
    }


}
