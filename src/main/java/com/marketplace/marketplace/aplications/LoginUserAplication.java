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

public class LoginUserAplication {
    MarketplaceApp marketplaceApp = new MarketplaceApp();

    @FXML
    public TextField username, password = new TextField();

    @FXML
    public Label message_error = new Label();

    @FXML
    public Button login, register = new Button();

    public LoginUserAplication() throws IOException {
    }

    public static void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginUserAplication.class.getResource("login_window.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 750, 500));
        stage.setResizable(false);
        stage.setTitle("Вход в учётную запись");
        stage.show();
    }

    public static void cancel(Button button) {
        Stage stage = (Stage)button.getScene().getWindow();
        stage.hide();
    }

    public boolean isValid() {
        return Objects.equals(username.getText(), RegisterUserAplication.getLoginArray()) &&
                Objects.equals(password.getText(), RegisterUserAplication.getPasswordArray());
    }
    private static final System.Logger log = System.getLogger(LoginUserAplication.class.getName());

    @FXML
    public void Login() throws IOException {
        if (isValid()) {
            message_error.setText("Вы ввели верные данные!");

            if (MarketplaceApp.role.size() == 0) {
                MarketplaceApp.role.add(0, Session.setRole(User.class));
            } else {
                MarketplaceApp.role.clear();
                MarketplaceApp.role.add(0, Session.setRole(User.class));
            }

            cancel(login);
            marketplaceApp.start(new Stage());
        } else {
            message_error.setText("Данные не верны!");

        }
    }

    @FXML
    private void Register() throws IOException {
        cancel(register);
        RegisterUserAplication.start(new Stage());
    }
}
