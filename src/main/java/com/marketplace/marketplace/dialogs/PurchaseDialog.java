package com.marketplace.marketplace.dialogs;

import com.marketplace.marketplace.aplications.ConnectionHandler;
import com.marketplace.marketplace.aplications.LoginUserAplication;
import com.marketplace.marketplace.aplications.MarketplaceApp;
import com.marketplace.marketplace.controllers.ControllerMarketplace;
import com.marketplace.marketplace.models.BankCard;
import com.marketplace.marketplace.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.marketplace.marketplace.aplications.LoginProviderAplication.currentProvider;
import static com.marketplace.marketplace.aplications.LoginUserAplication.currentUsername;
import static com.marketplace.marketplace.aplications.LoginUserAplication.isUser;
import static com.marketplace.marketplace.aplications.MarketplaceApp.*;
import static com.marketplace.marketplace.controllers.ControllerMarketplace.all;

public class PurchaseDialog {

    // кнопки отмены и оплаты.
    @FXML
    public Button cancel, purchase = new Button();

    //Лейблы для вывода инфы
    @FXML
    public Label index_products, error_message, bankcard = new Label();

    // список отображения карт
    public static Map<String, ObservableList<BankCard>> arrayBankCards = new HashMap<>();

    public static void start(Stage stage) throws IOException {
        //Загрузка и показ цены
        FXMLLoader fxmlLoader = new FXMLLoader(PurchaseDialog.class.getResource("purchase.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 500, 350));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.setTitle("Окно покупки и оплаты товара");
        stage.show();

    }

    @FXML
    public void initialize() {
        // показывает количество товаров в магазине
        index_products.setText(String.valueOf(arrayBasket.size()));
    }

    public String getBankCard() {
        // Возвращает конкретную карту
        for (String s: SelectionBankCardDialog.bankcards) {
            return s;
        }

        return null;
    }

    public void cancel(Button button) {
        //Кнопка выхода
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void Cancel() {
        cancel(cancel);
    }

    @FXML
    public void Purchase() {
        //Метод оплаты заказа, если корзина не пустая
        if (arrayBankCards.size() == 0) {
            error_message.setText("Банковская карта не добавлена!");
            return;
        }

        if (arrayBasket.size() == 0) {
            error_message.setText("Корзина пуста.");
            return;
        }

        Map<String, Integer> map = new HashMap<>();
        for (String product : arrayBasket) {
            map.merge(product, 1, Integer::sum);
        }

        String current = isUser ? currentUsername : currentProvider;
        System.out.println(current);

        for (String name : map.keySet()) {
            int idx = getIdx(name, current);
            int count = map.get(name);
            if (all.get(idx).number_product >= count) {
                int old = all.get(idx).number_product;
                all.get(idx).setNumberProduct(old - count);

                int index = 0;
                boolean isEnd = false;
                for (ObservableList<Product> value : arrayProducts.values()) {
                    if (isEnd) {
                        break;
                    }

                    for (Product product : value) {
                        if (idx == index) {
                            String sql = """
                             UPDATE market
                             SET quantity_prod = ?
                             WHERE name_prod = ?
                        """;
                            try (var statement = ConnectionHandler.getConnection().prepareStatement(sql)) {
                                statement.setInt(1, old - count);
                                statement.setString(2, product.name);

                                statement.executeUpdate();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            product.setNumberProduct(old - count);
                            isEnd = true;
                            break;
                        }
                        index++;
                    }
                }

                error_message.setText("Товар куплен!\nСпасибо за покупку, корзина очищена.");
                index_products.setText("0");
                arrayBasket.clear();
            } else {
                error_message.setText("Товаров в корзине больше, чем товаров на складе.");
                return;
            }
        }

        List<Product> l = new ArrayList<>(all);
        arrayBasket.clear();
        all.clear();
        all.setAll(l);
    }

    private static int getIdx(String name, String cur) {
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @FXML
    public void AddBankCard() throws IOException {
        //Открывает сцену добавление
        AddBankCardDialog.start(new Stage());
    }

    @FXML
    public void SelectedBankCard() throws IOException {
        //Запуск сцены выбора карты
        SelectionBankCardDialog.start(new Stage());
    }
}
