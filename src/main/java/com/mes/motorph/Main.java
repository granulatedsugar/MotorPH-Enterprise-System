package com.mes.motorph;

import com.mes.motorph.controller.LoginController;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private List<UserRole> userRoles;
    private int userId;
    private Stage loginStage; // Reference to the login stage

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 768, 746);
        Scene scene = new Scene(fxmlLoader.load(), 1280, 768);
        loginStage = new Stage(); // Initialize the class-level variable instead of declaring a local variable
        loginStage.setScene(scene);

        loginStage.setTitle("MotorPH Enterprise System V20240214");

        // Load the application icon
        Image icon = new Image(Main.class.getResourceAsStream("/images/app-icon.png"));
        loginStage.getIcons().add(icon);

        // Set reference to the login stage in the LoginController class
//        LoginController loginController = fxmlLoader.getController();
//        loginController.setMainApp(this);
//        loginController.setLoginStage(loginStage); // Pass the login stage reference

        loginStage.show();
    }

//    public void onLoginSuccess(List<UserRole> userRoles) {
//        this.userRoles = userRoles;
//        // Close the login stage
//        loginStage.close();
//        // Open the main stage
//        openMainStage();
//    }
//
//    private void openMainStage() {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 1280, 768);
//            Stage mainStage = new Stage();
//            mainStage.setScene(scene);
//            // Pass user ID and roles to the controller of main view
//            ViewFactory mainController = fxmlLoader.getController();
//            mainController.initData(userRoles);
//            mainStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        launch();
    }
}