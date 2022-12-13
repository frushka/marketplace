package com.marketplace.marketplace.dialogs;

import com.marketplace.marketplace.controllers.ControllerMarketplace;
import com.marketplace.marketplace.models.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class AddProductDialog {
    @FXML
    public Label message_error;

    @FXML
    public TextField name_product, number_product, price_product;

    @FXML
    public Button cancel, addProduct;
    public static void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddProductDialog.class.getResource("add_product.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 650, 450));
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Добавить продукт");
        stage.show();
    }

    @FXML
    public void Cancel() {
        cancel(cancel);
    }

    @FXML
    public void AddProduct() {
        if (isNotEmpty()) {
            message_error.setText("Добавление удалось!");
            try {

                /*System.out.println(getProductAbsolute(new Product(
                        name_product.getText(),
                        Integer.parseInt(number_product.getText()),
                        Double.parseDouble(price_product.getText())
                )));*/

                ControllerMarketplace.addProduct(new Product(
                        name_product.getText(),
                        Integer.parseInt(number_product.getText()),
                        Double.parseDouble(price_product.getText())
                ));

                cancel(addProduct);
            } catch (NumberFormatException numberFormatException) {
                message_error.setText("Вы ввели неверные данные ввода!");
            }

        } else {
            message_error.setText("Добавление товара не удалось!");
        }
    }

    public void cancel(Button button) {
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    public boolean isNotEmpty() {
        if (name_product.getText().isEmpty() || number_product.getText().isEmpty() || price_product.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
