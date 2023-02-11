package com.example.KingsMen.dao;

import com.example.KingsMen.db.MyConnection;
import com.example.KingsMen.model.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public List<accounts> getAllAccounts() throws SQLException {
        List<accounts> accounts = new ArrayList<>();
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from user");

        return accounts;
    }

}
