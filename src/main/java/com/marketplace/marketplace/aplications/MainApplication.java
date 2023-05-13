package com.marketplace.marketplace.aplications;

import com.marketplace.marketplace.models.Product;
import com.marketplace.marketplace.models.Provider;
import com.marketplace.marketplace.models.Session;
import com.marketplace.marketplace.models.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import static com.marketplace.marketplace.aplications.MarketplaceApp.arrayProducts;

public class MainApplication extends Application {
    @FXML
    public Button authProvider, authUser = new Button();//создание кнопок Я покупатель и Я поставщик
//    String driver = "com.mysql.cj.jdbc.Driver";
//    String url = "jdbc:mysql://localhost:3306/" + "marketplace?&serverTimezone=UTC";
//    String user = "root";
//    String password = "root";
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        /*Открытие stage и создание
         сцены выбора роли*/
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("auth.fxml")); //Создание объекта типа FXML
        stage.setScene(new Scene(fxmlLoader.load(), 700, 500));//загрузка FXML на сцену
        stage.setTitle("Выберете свою роль");//название сцены
        stage.setResizable(false);//Нельзя менять размер
        stage.show(); // показ сцены
    }

    public static void main(String[] args) {
        fill();
        launch(); // запуск всего проекта
    }

    @FXML
    public void AuthUser() throws IOException {
        /*Действие, которое вызывается после нажатия кнопки Я покупатель*/
        LoginUserAplication.cancel(authUser); // вызов метода, который после нажатия открывает сцену авторизации покупателя
        LoginUserAplication.start(new Stage());// вызов метода, который загружает FXML сцену авторизации покупателя
    }

    @FXML
    public void AuthProvider() throws IOException {
        /*Действие, которое вызывается после нажатия кнопки Я поставщик*/
        LoginProviderAplication.cancel(authProvider); // вызов метода, который после нажатия открывает сцену авторизации продавца
        LoginProviderAplication.start(new Stage()); // вызов метода, который загружает FXML сцену авторизации продавца
    }

    public static void fill() {
        String query = """
                SELECT *
                FROM sellers
        """;
        try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
            var result = statement.executeQuery();
            while (result.next()) {
                var login = result.getString("log_sel");
                arrayProducts.put(login, FXCollections.observableArrayList());
                String sql = """
                    SELECT *
                    FROM sel_wh
                    WHERE sellers_log_sel = ?
                    """ ;
                try (var st = ConnectionHandler.getConnection().prepareStatement(sql)) {
                    st.setString(1, login);
                    var r = st.executeQuery();
                    while (r.next()) {
                        String ss = """
                                SELECT *
                                FROM market
                                WHERE id_prod = ?
                                """;
                        try (var s = ConnectionHandler.getConnection().prepareStatement(ss)) {
                            s.setInt(1, r.getInt("market_id_prod"));
                            var rrr = s.executeQuery();
                            while (rrr.next()) {
                                arrayProducts.get(login).add(new Product(
                                        rrr.getString("name_prod"),
                                        rrr.getInt("quantity_prod"),
                                        rrr.getDouble("price_prod")
                                ));
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
