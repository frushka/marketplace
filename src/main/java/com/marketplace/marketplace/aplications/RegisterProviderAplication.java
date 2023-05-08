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
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterProviderAplication {
    @FXML
    public TextField username, name_company, password; // Текстовые поля ввода данных

    @FXML
    public Label error_message;// Выводит сообщение об ошибке, если поля пустые

    @FXML
    public Button register; // кнопка зарегистрироваться

//    static ArrayList<Provider> providers = new ArrayList<>(); // список всех продавцов

    public static void start(Stage stage) throws IOException {

        /*Загрузка и показ сцены регистрации продавца*/
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
        /* Проверяет пустоту ввода в поля */
        if(username.getText().isEmpty() || password.getText().isEmpty() || name_company.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void addProvider(Provider provider) {
        /* Метод добавления продавца в список*/
//        providers.add(provider);
        String query = """
                INSERT INTO sellers(log_sel, pwd_col, name_company)
                VALUES (?, ?, ?)
        """;
        try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
            statement.setString(1, provider.login);
            statement.setString(2, provider.password);
            statement.setString(3, provider.company_name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validateProvider(String login, String password) throws SQLException {
//        // получение логина продавца
//        for (Provider i: providers) {
//            if (i.getLogin().equals(login)
//                    && i.getPassword().equals(password)) {
//                return true;
//            }
//        }
//        return false;
        String query = """
                SELECT *
                FROM sellers
                WHERE log_sel = ?
                  AND pwd_col = ?
        """;
        try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            var result = statement.executeQuery();
            return result.next();
        }
    }

    @FXML
    public void RegProvider() throws IOException, SQLException {
        if (validateProvider(username.getText(), password.getText())) {
            error_message.setText("Этот аккаунт занят!");
            return;
        }

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
}
