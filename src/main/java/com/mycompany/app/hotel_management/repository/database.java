package com.mycompany.app.hotel_management.repository;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    public static Connection connectDb() {
        try {
            //
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_data", "root", "nhatcoi2004");

            // Load the MySQL JDBC driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            // Replace 'your_host' with the hostname or IP address of your database server
//            String host = "103.200.23.188";
//            String databaseName = "dichvut3_javafx";
//            String username = "dichvut3";
//            String password = "@6LQcpG9MBGqapE";
//
//            // Create the connection URL
//            String url = "jdbc:mysql://" + host + ":3306/" + databaseName;
//
//            // Establish the connection
//            return DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
