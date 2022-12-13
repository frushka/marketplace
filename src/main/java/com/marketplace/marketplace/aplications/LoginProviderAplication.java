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
import java.util.Objects;

import static com.marketplace.marketplace.aplications.MarketplaceApp.role;

public class LoginProviderAplication {
    @FXML
    public TextField username, password;

    @FXML
    public Button register, login = new Button();

    @FXML
    public Label message_error;

    MarketplaceApp marketplaceApp = new MarketplaceApp();

    public LoginProviderAplication() throws IOException {
    }

    public static void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterProviderAplication.class.getResource("login_provider_window.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 750, 500));
        stage.setTitle("Вход в учётную запись продавца");
        stage.setResizable(false);
        stage.show();
    }

    public boolean isValid() {
        return Objects.equals(username.getText(), RegisterProviderAplication.getLogin())
                && Objects.equals(password.getText(), RegisterProviderAplication.getPassword());
    }

    public static void cancel(Button button) {
        /* метод закрытия stage */
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void AuthProvider() throws IOException {
        if (isValid()) {

            if (MarketplaceApp.role.size() == 0) {
                MarketplaceApp.role.add(Session.setRole(Provider.class));
            } else {
                MarketplaceApp.role.clear();
                MarketplaceApp.role.add(0, Session.setRole(Provider.class));
            }
            cancel(login);
            marketplaceApp.start(new Stage());
        } else {
            message_error.setText("Учётная запись не найдена!");
        }
    }

    @FXML
    public void RegProvider() throws IOException {
        RegisterProviderAplication.cancel(register);
        RegisterProviderAplication.start(new Stage());
    }
}
