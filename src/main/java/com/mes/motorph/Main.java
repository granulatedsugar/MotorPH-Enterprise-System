package com.mes.motorph;

import com.mes.motorph.controller.LoginController;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage; // Reference to the primary stage
    private Stage loginStage; // Reference to the login stage
    private List<UserRole> userRoles;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openLoginStage();
    }

    public void openLoginStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 768, 746);
            loginStage = new Stage();
            loginStage.setResizable(false);
            loginStage.setScene(scene);
            loginStage.setTitle("MotorPH Enterprise System V20240214");

            // Load the application icon
            Image icon = new Image(Main.class.getResourceAsStream("/images/app-icon.png"));
            loginStage.getIcons().add(icon);

            // Set reference to the login stage in the LoginController class
            LoginController loginController = fxmlLoader.getController();
            loginController.setMainApp(this);
            loginController.setLoginStage(loginStage); // Pass the login stage reference

            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMainStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 768);
            primaryStage.setScene(scene);
            primaryStage.setTitle("MotorPH Enterprise System V20240214");

            // Load the application icon
            Image icon = new Image(Main.class.getResourceAsStream("/images/app-icon.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setResizable(true);

            // Pass user ID and roles to the controller of main view
            ViewFactory mainController = fxmlLoader.getController();
            mainController.setMainApp(this); // Set the main app reference here
            mainController.initData(userRoles);

            primaryStage.show();
        } catch (IOException | EmployeeException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        // Open the login stage
        openLoginStage();
        // Close the primary stage
        primaryStage.close();
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public static void main(String[] args) {
        launch();
    }
}
