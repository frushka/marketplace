/** Класс реализовывает добавление банковской карты в массив **/

package com.marketplace.marketplace.dialogs;

import com.marketplace.marketplace.models.BankCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class AddBankCardDialog {
    @FXML
    Button cancel;
    @FXML
    public TextField number_card, pin_code, cvv, owners_name, owners_surname = new TextField();
    @FXML
    public Label err_mes;

    public static void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddBankCardDialog.class.getResource("add_bankcard.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 500, 200));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Добавить банковскую карту");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.show();
    }

    public boolean isNotEmptyCardBank() {
        /* Проверяет на пустату поля ввода */
        if (number_card.getText().isEmpty() || pin_code.getText().isEmpty() || cvv.getText().isEmpty() ||
                owners_name.getText().isEmpty() || owners_surname.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void cancel(Button button) {
        /* метод закрытия stage */
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void Cancel() {
        cancel(cancel);
    }

    @FXML
    public void AddBankCard() {
        try {
            if (isNotEmptyCardBank()) {
                PurchaseDialog.arrayBankCards.add(new BankCard(
                        Integer.parseInt(number_card.getText()),
                        Integer.parseInt(pin_code.getText()),
                        cvv.getText(),
                        owners_name.getText(),
                        owners_surname.getText()
                ));
                cancel(cancel);
            }
        } catch (NumberFormatException numberFormatException) {
            err_mes.setText("Некорректный ввод");
            return;
        }
    }

}
