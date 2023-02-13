package com.example.KingsMen.model;

public class Account {
    private int account_id;
    private String account_fname;
    private String account_lname;
    private String account_email;
    private String account_password;
    private String account_address;
    private String account_type;

    public Account(int account_id, String account_fname, String account_lname, String account_email, String account_password, String account_address, String account_type) {
        this.account_id = account_id;
        this.account_fname = account_fname;
        this.account_lname = account_lname;
        this.account_email = account_email;
        this.account_password = account_password;
        this.account_address = account_address;
        this.account_type = account_type;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_fname() {
        return account_fname;
    }

    public void setAccount_fname(String account_fname) {
    }

    public String getAccount_lname() {
        return account_lname;
    }

    public void setAccount_lname(String account_lname) {
        this.account_lname = account_lname;
    }

    public String getAccount_email() {
        return account_email;
    }

    public void setAccount_email(String account_email) {
        this.account_email = account_email;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }

    public String getAccount_address() {
        return account_address;
    }

    public void setAccount_address(String account_address) {
        this.account_address = account_address;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    @Override
    public String toString() {
        return "accounts{" +
                "account_id=" + account_id +
                ", account_fname='" + account_fname + '\'' +
                ", account_lname='" + account_lname + '\'' +
                ", account_email='" + account_email + '\'' +
                ", account_password='" + account_password + '\'' +
                ", account_address='" + account_address + '\'' +
                ", account_type='" + account_type + '\'' +
                '}';
    }
}
