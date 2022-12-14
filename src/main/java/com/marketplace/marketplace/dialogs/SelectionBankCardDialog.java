package com.marketplace.marketplace.dialogs;

import com.marketplace.marketplace.models.BankCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.ArrayList;

import static com.marketplace.marketplace.aplications.LoginUserAplication.currentUsername;

public class SelectionBankCardDialog {
    PurchaseDialog purchaseDialog = new PurchaseDialog(); // создает класс оплаты заказа

    @FXML
    public ListView<BankCard> listview_bankcards = new ListView<>();//Лист отображения банковский карт

    public static ArrayList<String> bankcards = new ArrayList<>(); //Список карт

    @FXML
    public Button cancel, selected = new Button(); //Кнопки ответы и выбора

    public static void start(Stage stage) throws IOException {
        //Загрузка сцены, привязанной к этому классу
        FXMLLoader fxmlLoader = new FXMLLoader(AddBankCardDialog.class.getResource("selection_bankcard.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Выбрать банковскую карту");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void initialize() {
        // Присваивание списка карт списку отображения для вывода на маркетплейсе
        ObservableList<BankCard> list = PurchaseDialog.arrayBankCards.getOrDefault(currentUsername,
                FXCollections.observableArrayList());
        listview_bankcards.setItems(list);
    }

    public void cancel(Button button) {
        // Закрытие этой сцены
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void Cancel() {
        //Вызов метода закртия сцены
        cancel(cancel);
    }

    @FXML
    public void Selected() {
        //Вызов метода добавления карты
        getBankCard();
    }

    public void getBankCard() {
        // Возврат карты
        for (BankCard bk: listview_bankcards.getSelectionModel().getSelectedItems()) {
            purchaseDialog.bankcard.setText(bk.toString());
            cancel(cancel);
        }
    }
}
