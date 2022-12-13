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
    public PasswordField password = new PasswordField();

    @FXML
    public Button reg = new Button();

    @FXML
    public TextField login = new TextField();
    @FXML
    public TextField name = new TextField();
    @FXML
    public TextField surname = new TextField();
    @FXML
    public TextField adress = new TextField();
    @FXML
    public TextField patronymic = new TextField();

    @FXML
    public Label error_message = new Label();


    public static ArrayList<User> arrayList = new ArrayList<>();

    public static void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterUserAplication.class.getResource("register_window.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 750, 500));
        stage.setTitle("Регистрация покупателя");

        stage.setResizable(false);
        stage.show();
    }

    public boolean isNotNullTextField() {
        /* Проверяет на пустату поля ввода */
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
        arrayList.add(user);
    }

    public static String getLoginArray() {
        for (User i: arrayList) {
            return i.getLogin();
        }
        return null;
    }

    public static String getPasswordArray() {
        for (User i: arrayList) {
            return i.getPassword();
        }
        return null;
    }

    public static boolean getIsAddProduct() {
        for (User i: arrayList) {
            return i.isAddProduct();
        }

        return false;
    }
}
