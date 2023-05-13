/** С помощью данного класса реалезовываеться логика регистрации покупателя **/

package com.marketplace.marketplace.aplications;

import com.marketplace.marketplace.models.BankCard;
import com.marketplace.marketplace.models.Product;
import com.marketplace.marketplace.models.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.marketplace.marketplace.aplications.MarketplaceApp.arrayBasket;
import static com.marketplace.marketplace.dialogs.PurchaseDialog.arrayBankCards;

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
        if (validateUser(login.getText(), password.getText())) {
            error_message.setText("Этот аккаунт занят!");
            return;
        }

        /* Метод сначала проверяет не равны ли поля null, потом добавляет покупателя в массив */
        if(isNotNullTextField()) {
            error_message.setText("Регистрация прошла успешно!");

            LoginUserAplication.cancel(reg);
            LoginUserAplication.start(new Stage());

            //MarketplaceApp.role.add(0, Session.setRole(User.class));

            User user = new User(
                    login.getText(),
                    name.getText(),
                    surname.getText(),
                    patronymic.getText(),
                    password.getText(),
                    adress.getText()
            );
            addUser(user);
            String query = """
                INSERT INTO customers(log_ctms, pwd_ctms, adrs_ctms, first_name, last_name, patronymic)  
                VALUES (?, ?, ?, ?, ?, ?);
        """;
            try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
                statement.setString(1, user.login);
                statement.setString(2, user.password);
                statement.setString(3, user.address);
                statement.setString(4, user.name);
                statement.setString(5, user.surname);
                statement.setString(6, user.patronymic);

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            error_message.setText("Вы не заполнили все поля ввода");
        }
    }

    public void addUser(User user) {
        /*Добавление покупателя в список*/
        arrayList.add(user);
    }

    public static boolean validateUser(String login, String password) {
        String query = """
                SELECT *
                FROM customers
                WHERE log_ctms = ?
                  AND pwd_ctms = ?
        """;
        String sql = """
            SELECT *
            FROM cards
            WHERE customers_log_ctms = ?
        """;
        String sqql = """
            SELECT *
            FROM shop_cart
            WHERE customers_log_ctms = ?
        """;
        String sss = """
            SELECT *
            FROM market
            WHERE id_prod = ?
        """;
        try (var statement = ConnectionHandler.getConnection().prepareStatement(query);
        var st = ConnectionHandler.getConnection().prepareStatement(sql);
        var stt = ConnectionHandler.getConnection().prepareStatement(sqql);
        var sttt = ConnectionHandler.getConnection().prepareStatement(sss)) {
            statement.setString(1, login);
            statement.setString(2, password);
            var result = statement.executeQuery();
            if (result.next()) {
                st.setString(1, login);
                var r = st.executeQuery();
                while (r.next()) {
                    arrayBankCards.put(login, FXCollections.observableArrayList());
                    arrayBankCards.get(login).add(
                            new BankCard(
                                    r.getString("num_card"),
                                    r.getInt("pin_code"),
                                    r.getInt("cvv"),
                                    r.getString("owner_name"),
                                    r.getString("owner_surname")
                            )
                    );
                }
                stt.setString(1, login);
                var rr = stt.executeQuery();
                while (rr.next()) {
                    sttt.setInt(1, rr.getInt("market_id_prod"));
                    var f = sttt.executeQuery();
                    while (f.next()) {
                        arrayBasket.add(f.getString("name_prod"));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getIsAddProduct() {
        for (User i: arrayList) {
            return i.isAddProduct();
        }

        return false;
    }
}
