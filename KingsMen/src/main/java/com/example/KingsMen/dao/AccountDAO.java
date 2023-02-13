package com.example.KingsMen.dao;

import com.example.KingsMen.db.MyConnection;
import com.example.KingsMen.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public static List<Account> getAllAccounts() throws SQLException {
        List<Account> accountList = new ArrayList<>();
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from accounts");

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int account_id = rs.getInt(1);
            String account_fname = rs.getNString(2);
            String account_lname = rs.getNString(3);
            String account_email = rs.getNString(4);
            String account_password = rs.getNString(5);
            String account_address = rs.getNString(6);
            String account_type = rs.getNString(7);

//            Account acc = new Account(account_id,account_fname,account_lname,account_email,account_password,account_address,account_type);
//            accountList.add(acc);
        }
        return accountList;
    }

}
