package com.mycompany.app.hotel_management.repositories;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.sql.*;
import java.util.Objects;

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
            String url = "jdbc:mysql://" + host + ":3306/" + databaseName;

            // Establish the connection
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
