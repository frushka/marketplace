/** С помощью данного класса реалезовываеться логика регистрации покупателя **/

package com.marketplace.marketplace.aplications;

import com.marketplace.marketplace.models.Provider;
import com.marketplace.marketplace.models.Session;
import com.marketplace.marketplace.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterUserAplication {

    @FXML
    public PasswordField password = new PasswordField(); // поля ввода пароля

    @FXML
    public Button reg = new Button();// кнопка регистраци

    @FXML
    public TextField login = new TextField(); // поле ввода логина
    @FXML
    public TextField name = new TextField();// поле ввода имени
    @FXML
    public TextField surname = new TextField();// поле ввода фамилии
    @FXML
    public TextField adress = new TextField(); // поле ввода адреса
    @FXML
    public TextField patronymic = new TextField(); //поле ввода отчества

    @FXML
    public Label error_message = new Label();// штука выводит ошибку, если чел не заполнил поля


    public static ArrayList<User> arrayList = new ArrayList<>();//список всех покупателей

    public static void start(Stage stage) throws IOException {
        /*Метод загрузки и показа сцены*/
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterUserAplication.class.getResource("register_window.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 750, 500));
        stage.setTitle("Регистрация покупателя");

        stage.setResizable(false);
        stage.show();
    }

    public boolean isNotNullTextField() {
        /* Проверяет на пустоту
         поля ввода */
        if (login.getText().isEmpty() || surname.getText().isEmpty() ||
                password.getText().isEmpty() || patronymic.getText().isEmpty() ||
                adress.getText().isEmpty() || name.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public void RegisterUser() throws IOException {
        /* Метод сначала проверяет не равны ли поля null, потом добавляет покупателя в массив */
        if(isNotNullTextField()) {
            error_message.setText("Регистрация прошла успешно!");

            LoginUserAplication.cancel(reg);
            LoginUserAplication.start(new Stage());

            //MarketplaceApp.role.add(0, Session.setRole(User.class));

            addUser(new User(
                    login.getText(),
                    name.getText(),
                    surname.getText(),
                    patronymic.getText(),
                    password.getText(),
                    adress.getText()
            ));
        } else {
            error_message.setText("Вы не заполнили все поля ввода");
        }
    }

    public void addUser(User user) {
        /*Добавление покупателя в список*/
        arrayList.add(user);
    }

    public static boolean validate(String login, String password) {
        // получение логина покупателя
        for (User i: arrayList) {
            if (i.getLogin().equals(login)
             && i.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static boolean getIsAddProduct() {
        for (User i: arrayList) {
            return i.isAddProduct();
        }

        return false;
    }
}
