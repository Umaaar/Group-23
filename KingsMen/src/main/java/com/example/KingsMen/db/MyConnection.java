package com.example.KingsMen.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/kingsmen_uk","root","password");
            System.out.println("Connection Established");
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        return connection;

    }
}
