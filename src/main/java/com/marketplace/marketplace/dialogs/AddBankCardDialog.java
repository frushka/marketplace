/** Класс реализовывает добавление банковской карты в массив **/

package com.marketplace.marketplace.dialogs;

import com.marketplace.marketplace.aplications.ConnectionHandler;
import com.marketplace.marketplace.models.BankCard;
import javafx.collections.FXCollections;
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
import java.sql.SQLException;

import static com.marketplace.marketplace.aplications.LoginUserAplication.currentUsername;

public class AddBankCardDialog {

    /*Добавление карты*/
    @FXML
    Button cancel;// Кнопка отмены
    @FXML
    public TextField number_card, pin_code, cvv, owners_name, owners_surname = new TextField(); //поля карты
    @FXML
    public Label err_mes;//Вывод ошибки, если по полям что-то не так

    public static void start(Stage stage) throws IOException {
        /*Загрузка сцены добавления карты и
        показ этого окна*/
        FXMLLoader fxmlLoader = new FXMLLoader(AddBankCardDialog.class.getResource("add_bankcard.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 500, 200));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Добавить банковскую карту");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.show();
    }

    public boolean isNotEmptyCardBank() {
        /* Проверяет на пустоту поля ввода */
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
        /* Добавление карты в список*/
        try {
            if (isNotEmptyCardBank()) {
                System.out.println(number_card.getText().length() == 16);
                if (number_card.getText().length() == 16) {
                    BankCard card = new BankCard(
                            number_card.getText(),
                            Integer.parseInt(pin_code.getText()),
                            Integer.parseInt(cvv.getText()),
                            owners_name.getText(),
                            owners_surname.getText());
                    if (!PurchaseDialog.arrayBankCards.containsKey(currentUsername)) {
                        PurchaseDialog.arrayBankCards.put(currentUsername, FXCollections.observableArrayList());
                    }
                    PurchaseDialog.arrayBankCards.get(currentUsername).add(card);
                    cancel(cancel);

                    String query = """
                INSERT INTO cards(num_card, pin_code, cvv, customers_log_ctms, owner_name, owner_surname)
                VALUES (?, ?, ?, ?, ?, ?);
        """;
                    try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
                        statement.setString(1, card.number_card);
                        statement.setInt(2, card.pin_code);
                        statement.setInt(3, card.cvv);
                        statement.setString(4, currentUsername);
                        statement.setString(5, card.owners_name);
                        statement.setString(6, card.owners_surname);

                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException numberFormatException) {
            err_mes.setText("Некорректный ввод");
            return;
        }
    }

}
