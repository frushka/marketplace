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
    public Button authProvider, authUser = new Button();//создание кнопок Я покупатель и Я поставщик

    @Override
    public void start(Stage stage) throws IOException {
        /*Открытие stage и создание
         сцены выбора роли*/
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("auth.fxml")); //Создание объекта типа FXML
        stage.setScene(new Scene(fxmlLoader.load(), 700, 500));//загрузка FXML на сцену
        stage.setTitle("Выберете свою роль");//название сцены
        stage.setResizable(false);//Нельзя менять размер
        stage.show(); // показ сцены
    }

    public static void main(String[] args) {
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
}
