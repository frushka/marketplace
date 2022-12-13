/** С помощью данного класса реалезовываеться логика регистрации продавца **/

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
import java.util.ArrayList;

public class RegisterProviderAplication {
    @FXML
    public TextField username, name_company, password;

    @FXML
    public Label error_message;

    @FXML
    public Button register;

    static ArrayList<Provider> providers = new ArrayList<>();

    public static void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterProviderAplication.class.getResource("register_provider_window.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 750, 500));
        stage.setTitle(" Окно регистрации продавца");
        stage.setResizable(false);
        stage.show();
    }

    public static void cancel(Button button) {

        /* метод закрытия stage */
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    public boolean isNotEmpty() {
        /* Проверяет на пустату поля ввода */
        if(username.getText().isEmpty() || password.getText().isEmpty() || name_company.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void addProvider(Provider provider) {
        providers.add(provider);
    }

    public static String getLogin() {
        for (Provider p: providers) {
            return p.getLogin();
        }

        return null;
    }

    public static String getPassword() {
        for (Provider p: providers) {
            return p.getPassword();
        }

        return null;
    }

    @FXML
    public void RegProvider() throws IOException {
        /* Метод сначала проверяет не равны ли поля null, потом добавляет продавца в массив */
        if (isNotEmpty()) {
            error_message.setText("Вы заполнили все поля ввода!");
            LoginProviderAplication.cancel(register);
            LoginProviderAplication.start(new Stage());

            //MarketplaceApp.role.add(0, Session.setRole(Provider.class));

            addProvider(new Provider(
                    username.getText(),
                    password.getText(),
                    name_company.getText()
            ));
        } else {
            error_message.setText("Вы не заполнили все поля ввода!");
        }
        //System.out.println(getLogin());
    }

    public static boolean getIsPurchase() {
        for (Provider i: providers) {
            return i.isPurchase();
        }

        return false;
    }
}
