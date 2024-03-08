package com.mes.motorph.controller;

import com.mes.motorph.Main;
import com.mes.motorph.entity.User;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.services.UserRoleService;
import com.mes.motorph.services.UserService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LoginController {

    @FXML
    private MFXTextField usernameField;

    @FXML
    private MFXPasswordField passwordField;

    private UserService userService = new UserService();
    private UserRoleService userRoleService = new UserRoleService();
    private Main mainApp;
    private Stage loginStage;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        try {
            Optional<User> optionalUser = userService.authenticateUser(username, password);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<UserRole> userRoles = userRoleService.fetchUserRole(user.getUsername());

                // Notify successful login and open the main stage
                mainApp.setUserRoles(userRoles);
                mainApp.openMainStage();
                // Close the login stage
                loginStage.close();
            } else {
                // Notify invalid credentials
                AlertUtility.showAlert(Alert.AlertType.INFORMATION,"Information", null,"Invalid credentials. Please try again.");
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null,"An error occurred during login. Please try again.");
        }
    }


    @FXML
    private void openStepperStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/stepper.fxml"));
            Parent root = loader.load();

            Stage stepperStage = new Stage();
            stepperStage.setTitle("Set Password");
            stepperStage.initModality(Modality.APPLICATION_MODAL);
            stepperStage.setScene(new Scene(root));

            stepperStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Delete
//    @FXML
//    public void handleLogout(ActionEvent event) {
//        // Close the login stage
//        loginStage.close();
//        // Call logout method in Main class to handle the logout action
//        mainApp.logout();
//    }
//
//    private void showErrorMessage(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Login Error");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }
}
