package com.marketplace.marketplace.models;

public class Session {
    // сам маркетплейс

    public static String setRole(Object role) {

        if(role == Provider.class) {
            return "Provider";
        }
        if (role == User.class){
            return "User";
        }

        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
