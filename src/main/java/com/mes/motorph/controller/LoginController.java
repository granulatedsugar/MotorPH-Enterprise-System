package com.mes.motorph.controller;

import com.mes.motorph.Main;
import com.mes.motorph.entity.User;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.services.PasswordService;
import com.mes.motorph.services.UserRoleService;
import com.mes.motorph.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserService userService = new UserService();

    private UserRoleService userRoleService = new UserRoleService();
    private Main mainApp;
    private Stage loginStage;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Optional<User> optionalUser = userService.authenticateUser(username, password);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<UserRole> userRoles = userRoleService.fetchUserRole(user.getUsername());

                // Notify successful login and pass the user roles to the caller
              //  mainApp.onLoginSuccess(userRoles);
               // ((LoginApp) getApplication()).onLoginSuccess(userRoles);
            } else {
                // Notify invalid credentials
                showErrorMessage("Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("An error occurred during login. Please try again.");
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }
}
