package com.marketplace.marketplace.models;

import java.util.Objects;

public class User {
    //Наш любимый покупатель.
    public String login, name, surname, patronymic, password, address;

    public boolean add_product = true;
    public static String role = "User";

    public User(String login, String name, String surname,
                String patronymic, String password, String address) {

        this.login = login;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.password = password;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAddProduct() {
        return add_product;
    }

    public boolean isUser() {
        if (role == "User") {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "User";
    }
}
