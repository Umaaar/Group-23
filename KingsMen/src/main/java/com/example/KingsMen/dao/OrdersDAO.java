package com.example.KingsMen.dao;

import com.example.KingsMen.db.MyConnection;
import com.example.KingsMen.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    public static List<Order> getAllorders() throws SQLException {
        List<Order> orderList = new ArrayList<>();
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

             Order newOrder = new Order(order_id,order_name,order_email,order_product_id,order_status,order_total);
             orderList.add(newOrder);
        }
        return orderList;
    }


}
