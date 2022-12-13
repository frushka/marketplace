package com.marketplace.marketplace.controllers;

import com.marketplace.marketplace.aplications.*;
import com.marketplace.marketplace.dialogs.AddProductDialog;
import com.marketplace.marketplace.dialogs.PurchaseDialog;
import com.marketplace.marketplace.models.Product;
import com.marketplace.marketplace.models.Provider;
import com.marketplace.marketplace.models.Session;
import com.marketplace.marketplace.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.marketplace.marketplace.aplications.MarketplaceApp.*;

public class ControllerMarketplace {
    public Button addProduct, purchase, deleteProductButton, sign_out;

    public ListView<Product> list_products;

    public ListView<String> basket;

    @FXML
    VBox product_buttons;

    public Label index_product;


    @FXML
    public void initialize() {
        listViewProducts();
        SelectionItemProduct();
        listViewBasket();
        SelectionItemBasket();
        product_buttons.setVisible(isProvider());

        purchase.setVisible(isUser());
    }

    public boolean isProvider() {
        return Objects.equals(getRoleArray(), "Provider");
    }

    public boolean isUser() {
        return Objects.equals(getRoleArray(), "User");
    }
    public void listViewProducts() {
        list_products.setItems(arrayProducts);
    }

    public void listViewBasket() {
        basket.setItems(arrayBasket);
    }

    public static void addProductBasket(String name) {
        arrayBasket.add(name);
    }

    public static void addProduct(Product product) {
        arrayProducts.add(product);
    }
    @FXML
    public void AddProduct() throws IOException {
        AddProductDialog.start(new Stage());
    }

    @FXML
    public void AddBasket () {
        for (Product i : list_products.getSelectionModel().getSelectedItems()) {
            if (i.getNumberProduct() > 0) {
                arrayBasket.add(i.getName());
            }
        }
        index_product.setText(String.valueOf(arrayBasket.size()));
    }

    @FXML
    public void RemoveBasket () {
        try {
            for (String i : basket.getSelectionModel().getSelectedItems()) {
                arrayBasket.remove(i);
            }
            index_product.setText(String.valueOf(arrayBasket.size()));
        } catch (NoSuchElementException noSuchElementException) {
            return;
        }
    }

    @FXML
    public void Purchase () throws IOException {
        PurchaseDialog.start(new Stage());
    }

    @FXML
    public void DeleteProductButton () {
        try {
            for (Product i : list_products.getSelectionModel().getSelectedItems()) {
                arrayProducts.remove(i);
            }
        } catch (NoSuchElementException noSuchElementException) {
            return;
        }
    }

    public void SelectionItemProduct () {
        MultipleSelectionModel<Product> selectionModel = list_products.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void SelectionItemBasket () {
        MultipleSelectionModel<String> selectionModel = basket.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    }

    MainApplication mainApplication = new MainApplication();

    @FXML
    public void SignOut() throws IOException {
        MarketplaceApp.cancel(sign_out);
        mainApplication.start(new Stage());
        if (role.size() > 0) {
            MarketplaceApp.role.clear();
        }
    }
}
