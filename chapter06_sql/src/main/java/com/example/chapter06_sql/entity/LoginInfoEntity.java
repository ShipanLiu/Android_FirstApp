package com.example.chapter06_sql.entity;

public class LoginInfoEntity {
    public int id;
    public String phone;
    public String password;
    public boolean remember = false;

    public LoginInfoEntity(){}


    public LoginInfoEntity(String phone, String password, boolean remember) {
        this.phone = phone;
        this.password = password;
        this.remember = remember;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }
}
