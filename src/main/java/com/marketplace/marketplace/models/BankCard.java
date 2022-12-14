package com.marketplace.marketplace.models;

public class BankCard {
    public int number_card, pin_code,cvv;
    public String  owners_name, owners_surname;

    public BankCard(int number_card, int pin_code, int cvv, String owners_name, String owners_surname) {
        // конструктор
        this.number_card = number_card;
        this.pin_code = pin_code;
        this.cvv = cvv;
        this.owners_name = owners_name;
        this.owners_surname = owners_surname;
    }
    public BankCard(int number_card, int pin_code, int cvv){
        this.number_card=number_card;
        this.pin_code=pin_code;
        this.cvv=cvv;
    }

    public int getNumberCard() {
        return number_card;
    }

    public int getPinCode() {
        return pin_code;
    }

    public int getCVV() {
        return cvv;
    }

    public String getOwnersName() {
        return owners_name;
    }

    public String getOwnersSurname() {
        return owners_surname;
    }

    public void setCVV(int cvv) {
        this.cvv = cvv;
    }

    public void setNumberCard(int number_card) {
        this.number_card = number_card;
    }

    public void setOwnersName(String owners_name) {
        this.owners_name = owners_name;
    }

    public void setOwnersSurname(String owners_surname) {
        this.owners_surname = owners_surname;
    }

    public void setPinCode(int pin_code) {
        this.pin_code = pin_code;
    }

    @Override
    public String toString() {
        return "Номер карты: " + getNumberCard() + " | " + "Имя: " + getOwnersName() + " | " + "Фамилия: "
                + getOwnersSurname();
    }
}
