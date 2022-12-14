package com.marketplace.marketplace.dialogs;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.marketplace.marketplace.aplications.MarketplaceApp.*;

public class PurchaseDialog {

    // кнопки отмены и оплаты.
    @FXML
    public Button cancel, purchase = new Button();

    //Лейблы для вывода инфы
    @FXML
    public Label index_products, error_message, bankcard = new Label();

    // список отображения карт
    public static ObservableList<BankCard> arrayBankCards = FXCollections.observableArrayList();

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

        for (String name : map.keySet()) {
            int idx = getIdx(name);
            int count = map.get(name);
            if (arrayProducts.get(idx).number_product >= count) {
                int old = arrayProducts.get(idx).number_product;
                arrayProducts.get(idx).setNumberProduct(old - count);
                error_message.setText("Товар куплен!\nСпасибо за покупку, корзина очищена.");
                index_products.setText("0");
                arrayBasket.clear();
            } else {
                error_message.setText("Товаров в корзине больше, чем товаров на складе.");
                return;
            }
        }

        List<Product> bufList = new ArrayList<>(arrayProducts);

        arrayProducts.clear();
        arrayBasket.clear();
        arrayProducts.addAll(bufList);
    }

    private static int getIdx(String name) {
        for (int i = 0; i < arrayProducts.size(); i++) {
            if (arrayProducts.get(i).name.equals(name)) {
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
