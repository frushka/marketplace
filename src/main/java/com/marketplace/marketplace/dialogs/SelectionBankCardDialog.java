package com.marketplace.marketplace.dialogs;

import com.marketplace.marketplace.models.BankCard;
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

public class SelectionBankCardDialog {
    PurchaseDialog purchaseDialog = new PurchaseDialog();

    @FXML
    public ListView<BankCard> listview_bankcards = new ListView<>();

    public static ArrayList<String> bankcards = new ArrayList<>();

    @FXML
    public Button cancel, selected = new Button();

    public static void start(Stage stage) throws IOException {
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
        listview_bankcards.setItems(PurchaseDialog.arrayBankCards);
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
    public void Selected() {
        getBankCard();
    }

    public void getBankCard() {
        for (BankCard bk: listview_bankcards.getSelectionModel().getSelectedItems()) {
            purchaseDialog.bankcard.setText(bk.toString());
            cancel(cancel);
        }
    }
}
