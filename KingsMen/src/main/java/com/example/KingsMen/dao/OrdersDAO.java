package com.example.KingsMen.dao;

import com.example.KingsMen.db.MyConnection;
import com.example.KingsMen.model.orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    public static List<orders> getAllorders() throws SQLException {
        List<orders> ordersList = new ArrayList<>();
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from orders");

        ResultSet rp = ps.executeQuery();

        while(rp.next()){
             int order_id = rp.getInt(1);
             String order_name = rp.getString(2);
             String order_email = rp.getString(3);
             int order_product_id = rp.getInt(4);
             String order_status = rp.getString(5);
             int order_total  = rp.getInt(6);

             orders newOrder = new orders(order_id,order_name,order_email,order_product_id,order_status,order_total);
             ordersList.add(newOrder);
        }
        return ordersList;
    }


}