package com.marketplace.marketplace.aplications;

import com.marketplace.marketplace.models.Provider;
import com.marketplace.marketplace.models.Session;
import com.marketplace.marketplace.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import static com.marketplace.marketplace.aplications.MarketplaceApp.role;

public class LoginProviderAplication {

    public static String currentProvider;

    @FXML
    public TextField username, password; // Текстовые поля для ввода логина и пароля

    @FXML
    public Button register, login = new Button();// кнопки авторизации и регистрации

    @FXML
    public Label message_error;// Штука для вывода ошибки, если пользователь не найден или данные некорректны


    MarketplaceApp marketplaceApp = new MarketplaceApp();//создание Маркетплейса для пользователя

    public LoginProviderAplication() throws IOException {
        /* Конструктор класса авторизации микрочелика под статусом покупатель*/
    }

    public static void start(Stage stage) throws IOException {
        /* Загрузка FXML авторизации и вставка её на сцену */
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterProviderAplication.class.getResource("login_provider_window.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 750, 500));
        stage.setTitle("Вход в учётную запись продавца");
        stage.setResizable(false);
        stage.show();
    }

    public boolean isValid() {
        /* Проверка соответствия полей. Чел зарегался, а потом авторизируется и тут проверка.
         * Да и просто проверка наличия в списке*/
        return RegisterProviderAplication.validateProvider(username.getText(), password.getText());
    }

    public static void cancel(Button button) {
        /* метод закрытия stage */
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void AuthProvider() throws IOException {
        /*Вызывает валидацию, чтобы чекнуть норм чел или не норм*/
        if (isValid()) {

            if (MarketplaceApp.role.size() == 0) {
                MarketplaceApp.role.add(Session.setRole(Provider.class));
            } else {
                MarketplaceApp.role.clear();
                MarketplaceApp.role.add(0, Session.setRole(Provider.class));
            }
            cancel(login);
            currentProvider = username.getText();
            LoginUserAplication.isUser = false;
            marketplaceApp.start(new Stage());
        } else {
            message_error.setText("Учётная запись не найдена!");
        }
    }

    @FXML
    public void RegProvider() throws IOException {
        /*Вызывает штуку при нажатии кнопки регистрации,
          тут же запускается сцена регистрации*/
        RegisterProviderAplication.cancel(register);
        RegisterProviderAplication.start(new Stage());
    }
}
