package com.marketplace.marketplace.models;

import java.util.UUID;

public class Product {
    public UUID id;
    // класс продуктов
    public String name;
    public int number_product;
    public double price_product;

    public Product(String name, int number_product, double price_product) {
        id = UUID.randomUUID();
        this.name = name;
        this.number_product = number_product;
        this.price_product = price_product;
    }

    public String getName() {
        return name;
    }

    public double getPriceProduct() {
        return price_product;
    }

    public int getNumberProduct() {
        return number_product;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberProduct(int number_product) {
        this.number_product = number_product;
    }

    public void setPriceProduct(double price_product) {
        this.price_product = price_product;
    }

    @Override
    public String toString() {
        return "Название товара: " + getName() + " | " + "Количество товаров: " + getNumberProduct() +
                " | " + "Стоимость товара: " + getPriceProduct();
    }

}
