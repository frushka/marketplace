package com.marketplace.marketplace.controllers;

import com.marketplace.marketplace.aplications.*;
import com.marketplace.marketplace.dialogs.AddProductDialog;
import com.marketplace.marketplace.dialogs.PurchaseDialog;
import com.marketplace.marketplace.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.marketplace.marketplace.aplications.MarketplaceApp.*;

public class ControllerMarketplace {
    public Button addProduct, purchase, deleteProductButton, sign_out; // кнопки добавки, покупки, удаления и выхода

    public ListView<Product> list_products;// список показа элементов маркера

    @FXML
    public Label nameBasket = new Label();
    public ListView<String> basket;//список показа элемента корзины

    @FXML
    VBox product_buttons, buy_buttons;// поля для кнопок

   // public Label index_product;


    @FXML
    public void initialize() {
        /*Вызывается при создании сцены, чтобы вывести начальные поля*/
        listViewProducts();
        SelectionItemProduct();
        listViewBasket();
        SelectionItemBasket();
        product_buttons.setVisible(isProvider());
        basket.setVisible(isUser());
        nameBasket.setVisible(isUser());
        purchase.setVisible(isUser());
        buy_buttons.setVisible(isUser());

    }

    public boolean isProvider() {
        // установка видимости кнопок, если пользователь продавец
        return Objects.equals(getRoleArray(), "Provider");
    }

    public boolean isUser() {
        // установка видимости кнопок, если пользователь покупатель
        return Objects.equals(getRoleArray(), "User");
    }
    public void listViewProducts() {
        //передача в список на фронт списка продуктов маркетплейса
        list_products.setItems(arrayProducts);
    }

    public void listViewBasket() {
        // передача в список на фронт списка корзины
        basket.setItems(arrayBasket);
    }

    public static void addProductBasket(String name) {
        //Добавление элементов в корзину
        arrayBasket.add(name);
    }

    public static void addProduct(Product product) {
        //Добавление элементов на маркетплейс в список
        arrayProducts.add(product);
    }
    @FXML
    public void AddProduct() throws IOException {
        // Открывает окно для продавца добавления элементов
        AddProductDialog.start(new Stage());
    }

    @FXML
    public void AddBasket () {
        // добавление элементов в корзину на фронте
        for (Product i : list_products.getSelectionModel().getSelectedItems()) {
            if (i.getNumberProduct() > 0) {
                arrayBasket.add(i.getName());
            }
        }
       // index_product.setText(String.valueOf(arrayBasket.size()));
    }

    @FXML
    public void RemoveBasket () {
        // удаление элементов из корзины
        try {
            for (String i : basket.getSelectionModel().getSelectedItems()) {
                arrayBasket.remove(i);
            }
            //index_product.setText(String.valueOf(arrayBasket.size()));
        } catch (NoSuchElementException noSuchElementException) {
            return;
        }
    }

    @FXML
    public void Purchase () throws IOException {
        // запуск окна оплаты
        PurchaseDialog.start(new Stage());
    }

    @FXML
    public void DeleteProductButton () {
        //кнопки удаления продукта
        try {
            for (Product i : list_products.getSelectionModel().getSelectedItems()) {
                arrayProducts.remove(i);
            }
        } catch (NoSuchElementException noSuchElementException) {
            return;
        }
    }

    public void SelectionItemProduct () {
        //Помогает осуществлять множественный выбор на маркете
        MultipleSelectionModel<Product> selectionModel = list_products.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void SelectionItemBasket () {
        // Помогает осуществлять множественный выбор в корзине
        MultipleSelectionModel<String> selectionModel = basket.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    }

    //создание основного запуска
    MainApplication mainApplication = new MainApplication();

    @FXML
    public void SignOut() throws IOException {
        //кнопка выхода из аккаунта
        MarketplaceApp.cancel(sign_out);
        mainApplication.start(new Stage());
        if (role.size() > 0) {
            MarketplaceApp.role.clear();
        }
    }
}
