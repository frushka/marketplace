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
import java.util.ArrayList;
import java.util.Objects;

public class MarketplaceApp {

    public static ObservableList<String> arrayBasket = FXCollections.observableArrayList();

    public static ArrayList<String> role = new ArrayList<>();
    public static ObservableList<Product> arrayProducts = FXCollections.observableArrayList(
            new Product("Предмет №1", 1, 1),
            new Product("Предмет №2", 2, 2),
            new Product("Предмет №3", 3, 3),
            new Product("Предмет №4", 4, 4),
            new Product("Предмет №5", 5, 5)
    );

    FXMLLoader fxmlLoader = new FXMLLoader(MarketplaceApp.class.getResource("marketplace.fxml"));
    //ControllerMarketplace controllerMarketplace = fxmlLoader.getController();

    public void start(Stage stage) throws IOException {
        
        stage.setScene(new Scene(fxmlLoader.load(), 900, 650));
        stage.setResizable(false);
        stage.setTitle("Маркетплейс" + getRole());
        /*if (arrayBasket.size() == 0) {
            controllerMarketplace.purchase.setDisable(true);
        }*/


        stage.show();
    }

    public String getRole() {
        if(Objects.equals(getRoleArray(), "Provider")) {
            return " сцена поставщика";
        }
        if (Objects.equals(getRoleArray(), "User")){
            return " сцена покупателя";
        }
        return null;
    }

    public static void cancel(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.hide();
    }

    public static String getRoleArray() {
        for (String string: role) {
            return string;
        }

        return null;
    }
}
