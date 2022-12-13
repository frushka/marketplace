package com.marketplace.marketplace.dialogs;

import com.marketplace.marketplace.aplications.MarketplaceApp;
import com.marketplace.marketplace.models.BankCard;
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

public class PurchaseDialog {
    @FXML
    public Button cancel, purchase = new Button();

    @FXML
    public Label index_products, error_message, bankcard = new Label();

    public static ObservableList<BankCard> arrayBankCards = FXCollections.observableArrayList();

    public static void start(Stage stage) throws IOException {
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
        index_products.setText(String.valueOf(MarketplaceApp.arrayBasket.size()));

    }

    public String getBankCard() {
        for (String s: SelectionBankCardDialog.bankcards) {
            return s;
        }

        return null;
    }

    public void cancel(Button button) {
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void Cancel() {
        cancel(cancel);
    }

    @FXML
    public void Purchase() {
        if (arrayBankCards.size() != 0) {
            error_message.setText("Банковская карта добавлена! Можно оплатить и купить товар");
        } else {
            error_message.setText("Банковская карта не добавлена!");
        }
    }

    @FXML
    public void AddBankCard() throws IOException {
        AddBankCardDialog.start(new Stage());
    }

    @FXML
    public void SelectedBankCard() throws IOException {
        SelectionBankCardDialog.start(new Stage());
    }
}
