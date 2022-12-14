package com.marketplace.marketplace.models;

import javafx.fxml.FXML;

import java.util.Objects;

public class Provider {
    //класс продавцов
    public String login, password,company_name, name_product, number_product, price_product;

    public boolean purchase = true;

    public static String role = "Provider";

    public Provider(String login, String password,String company_name) {
        this.login = login;
        this.password = password;
        this.company_name = company_name;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getCompanyName() {
        return company_name;
    }

    public String getNameProduct() {
        return name_product;
    }

    public String getNumberProduct() {
        return number_product;
    }

    public String getPriceProduct() {
        return price_product;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setCompanyName(String company_name) {
        this.company_name = company_name;
    }

    public boolean isPurchase() {
        return purchase;
    }

    public static boolean isProvider() {
        return !Objects.equals(role, "Provider");
    }

    @Override
    public String toString() {
       //return "Логин: " + getLogin() + " | " + "Название компании: " +
       //        getCompanyName() + " | " + "Пароль: " + getPassword();
        return "Provider";
    }
}
