package com.marketplace.marketplace.aplications;

import com.marketplace.marketplace.controllers.ControllerMarketplace;
import com.marketplace.marketplace.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MarketplaceApp {

    //Эта штука нужна, чтобы в реально времени отображать и менять состояние корзины
    public static ObservableList<String> arrayBasket = FXCollections.observableArrayList();

    //Список человек, которые есть на маркете
    public static ArrayList<String> role = new ArrayList<>();

    // Список товаров, которые есть на маркетплейсе
    public static Map<String, ObservableList<Product>> arrayProducts = new HashMap<>() {{
        var connection = ConnectionHandler.getConnection();
        String query = """
            SELECT *
            FROM sel_wh
        """;
        try (var statement = ConnectionHandler.getConnection().prepareStatement(query)) {
            var result = statement.executeQuery();
            String q = """
                    SELECT *
                    FROM market
                    where id_prod = ?
                """;
            while (result.next()) {
                try (var st = ConnectionHandler.getConnection().prepareStatement(q)) {
                    st.setInt(1, result.getInt("market_id"));
                    var r = st.executeQuery();
                    ObservableList<Product> p = FXCollections.observableArrayList();
                    while (r.next()) {
                        p.add(new Product(r.getString("name_prod"), r.getInt("quantity_prod"), r.getDouble("price_prod")));
                    }
                    put(result.getString("sellers_log_sel"), p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }};

    //Загрузка сцены из FXML
    FXMLLoader fxmlLoader = new FXMLLoader(MarketplaceApp.class.getResource("marketplace.fxml"));

    public void start(Stage stage) throws IOException {

        /*Штука, которая показывает сцену*/

        stage.setScene(new Scene(fxmlLoader.load(), 900, 650));
        stage.setResizable(false);
        stage.setTitle("Маркетплейс" + getRole());



        /*if (arrayBasket.size() == 0) {
            controllerMarketplace.purchase.setDisable(true);
        }*/


        stage.show(); //показ сцены
    }

    public String getRole() {
        /* дает название окну,
        в котором вызывается сцена.*/
        if(Objects.equals(getRoleArray(), "Provider")) {
            return " сцена поставщика";
        }
        if (Objects.equals(getRoleArray(), "User")){
            return " сцена покупателя";
        }
        return null;
    }

    public static void cancel(Button button) {
        /*Метод выхода из маркета */
        Stage stage = (Stage) button.getScene().getWindow();
        stage.hide();
    }

    public static String getRoleArray() {
        /* Метод, который пробегает по всем ролям*/
        for (String string: role) {
            return string;
        }

        return null;
    }
}
