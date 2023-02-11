package com.example.KingsMen.model;

public class accounts {
    private int account_id;
    private String account_fname;
    private String account_lname;
    private String account_email;
    private String account_password;
    private String account_address;
    private String account_type;

    public accounts(int account_id, String account_fname, String account_lname, String account_email, String account_password, String account_address, String account_type) {
        this.account_id = account_id;
        this.account_fname = account_fname;
        this.account_lname = account_lname;
        this.account_email = account_email;
        this.account_password = account_password;
        this.account_address = account_address;
        this.account_type = account_type;
    }
}
