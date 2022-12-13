package com.marketplace.marketplace.aplications;

import com.marketplace.marketplace.models.Provider;
import com.marketplace.marketplace.models.Session;
import com.marketplace.marketplace.models.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class MainApplication extends Application {
    @FXML
    public Button authProvider, authUser = new Button();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("auth.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 700, 500));
        stage.setTitle("Выберете свою роль");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    public void AuthUser() throws IOException {
        LoginUserAplication.cancel(authUser);
        LoginUserAplication.start(new Stage());
    }

    @FXML
    public void AuthProvider() throws IOException {
        LoginProviderAplication.cancel(authProvider);
        LoginProviderAplication.start(new Stage());
    }
}
