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
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.marketplace.marketplace.aplications.LoginProviderAplication.currentProvider;
import static com.marketplace.marketplace.aplications.LoginUserAplication.currentUsername;
import static com.marketplace.marketplace.aplications.LoginUserAplication.isUser;
import static com.marketplace.marketplace.aplications.MarketplaceApp.*;

public class ControllerMarketplace {
    public Button addProduct, purchase, deleteProductButton, sign_out; // кнопки добавки, покупки, удаления и выхода

    public ListView<Product> list_products;// список показа элементов маркера
    public static ObservableList<Product> all = FXCollections.observableArrayList();// список показа элементов маркера

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
        String current = isUser ? currentUsername : currentProvider;

        all.clear();
        if (current.equals(currentUsername)) {
            for (ObservableList<Product> value : arrayProducts.values()) {
                all.addAll(value);
            }

            list_products.setItems(all);
            return;
        }

        if (!arrayProducts.containsKey(current)) {
            arrayProducts.put(current, FXCollections.observableArrayList());
        }

        list_products.setItems(arrayProducts.get(current));
    }

    public void listViewBasket() {
        // передача в список на фронт списка корзины
        basket.setItems(arrayBasket);
    }

    public static void addProductBasket(String name) {
        //Добавление элементов в корзину
        arrayBasket.add(name);
        String query = """
            SELECT *
            FROM market
            WHERE name_prod = ?
        """;
        try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            var result = statement.executeQuery();
            while (result.next()) {
                String sql = """
                INSERT INTO shop_cart(market_id_prod, customers_log_ctms)
                VALUES (?, ?)
                """;
                try (var s = ConnectionHandler.getConnection().prepareStatement(sql)) {
                    s.setInt(1, result.getInt("id_prod"));
                    s.setString(2, currentUsername);
                    s.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addProduct(Product product) {
        //Добавление элементов на маркетплейс в список
        arrayProducts.get(currentProvider).add(product);
        String query = """
                INSERT INTO market(name_prod, quantity_prod, price_prod)
                VALUES (?, ?, ?)
        """;
        String sq = """
                SELECT LAST_INSERT_ID() as id_prod;
        """;
        try (var statement = ConnectionHandler.getConnection().prepareStatement(query);
        var st = ConnectionHandler.getConnection().prepareStatement(sq)) {
            statement.setString(1, product.name);
            statement.setInt(2, product.number_product);
            statement.setDouble(3, product.price_product);

            statement.executeUpdate();

            var result = st.executeQuery();
            while (result.next()) {
                String sql = """
                INSERT INTO sel_wh(market_id_prod, sellers_log_sel)
                VALUES (?, ?)
                """;
                try (var s = ConnectionHandler.getConnection().prepareStatement(sql)) {
                    s.setInt(1, result.getInt("id_prod"));
                    s.setString(2, currentProvider);
                    s.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                String query = """
            SELECT *
            FROM market
            WHERE name_prod = ?
        """;
                try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
                    statement.setString(1, i.getName());
                    var result = statement.executeQuery();
                    while (result.next()) {
                        String sql = """
                INSERT INTO shop_cart(market_id_prod, customers_log_ctms)
                VALUES (?, ?)
                """;
                        try (var s = ConnectionHandler.getConnection().prepareStatement(sql)) {
                            s.setInt(1, result.getInt("id_prod"));
                            s.setString(2, currentUsername);
                            s.executeUpdate();
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                String query = """
                    SELECT *
                    FROM market
                    WHERE name_prod = ?
                """;
                try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
                    statement.setString(1, i);
                    var result = statement.executeQuery();
                    while (result.next()) {
                        String sql = """
                            DELETE
                            FROM shop_cart
                            WHERE market_id_prod = ?
                         """;
                        try (var s = ConnectionHandler.getConnection().prepareStatement(sql)) {
                            s.setInt(1, result.getInt("id_prod"));
                            s.executeUpdate();
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
        ObservableList<Product> pr = list_products.getSelectionModel().getSelectedItems();
        ObservableList<Product> cur = arrayProducts.get(currentProvider);

        for (Product product : pr) {
            String query = """
                DELETE
                FROM market
                WHERE name_prod = ?
            """;
            try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
                statement.setString(1, product.name);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        cur.removeAll(pr);
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
        try {
            mainApplication.start(new Stage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (role.size() > 0) {
            MarketplaceApp.role.clear();
        }
    }
}
