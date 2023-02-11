package com.example.KingsMen.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:1433/youtubelive","root","Zafar@2020");
            System.out.println("Connection Established");
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        return connection;

    }
}
