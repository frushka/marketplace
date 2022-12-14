package com.marketplace.marketplace.aplications;

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

public class LoginUserAplication {

    public static String currentUsername;
    public static boolean isUser = false;

    MarketplaceApp marketplaceApp = new MarketplaceApp(); //создание Маркетплейса для пользователя

    @FXML
    public TextField username, password = new TextField(); // Текстовые поля для ввода логина и пароля

    @FXML
    public Label message_error = new Label(); // Штука для вывода ошибки, если пользователь не найден или данные некорректны

    @FXML
    public Button login, register = new Button();// кнопки авторизации и регистрации

    public LoginUserAplication() throws IOException {
        /* Конструктор класса авторизации микрочелика под статусом покупатель*/
    }

    public static void start(Stage stage) throws IOException {
        /* Загрузка FXML авторизации и вставка её на сцену */
        FXMLLoader fxmlLoader = new FXMLLoader(LoginUserAplication.class.getResource("login_window.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 750, 500));
        stage.setResizable(false);
        stage.setTitle("Вход в учётную запись");
        stage.show();
    }

    public static void cancel(Button button) {
        /* Метод, который сворачивает сцену после нажатия кнопки*/
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    public boolean isValid() {
        /* Проверка соответствия полей. Чел зарегался, а потом авторизируется и тут проверка.
        * Да и просто проверка наличия в списке*/
        return RegisterUserAplication.validateUser(username.getText(), password.getText());
    }
    //Получение экземпляра класса
    private static final System.Logger log = System.getLogger(LoginUserAplication.class.getName());

    @FXML
    public void Login() throws IOException {
        /*Вызывает валидацию, чтобы чекнуть норм чел или не норм*/
        if (isValid()) {
            message_error.setText("Вы ввели верные данные!");

            if (MarketplaceApp.role.size() == 0) {
                MarketplaceApp.role.add(0, Session.setRole(User.class));
            } else {
                MarketplaceApp.role.clear();
                MarketplaceApp.role.add(0, Session.setRole(User.class));
            }

            currentUsername = username.getText();
            isUser = true;
            cancel(login);
            marketplaceApp.start(new Stage());
        } else {
            message_error.setText("Данные не верны!");

        }
    }

    @FXML
    private void Register() throws IOException {
        /*Вызывает штуку при нажатии кнопки регистрации,
          тут же запускается сцена регистрации*/
        cancel(register);
        RegisterUserAplication.start(new Stage());
    }
}
