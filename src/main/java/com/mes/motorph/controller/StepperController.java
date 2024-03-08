package com.mes.motorph.controller;

import com.mes.motorph.entity.User;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.services.PasswordService;
import com.mes.motorph.services.UserService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;


public class StepperController {

    @FXML
    private MFXTextField usernameField;
    @FXML
    private MFXPasswordField passwordField;
    @FXML
    private MFXButton submitButton;
    @FXML
    private MFXButton successBtn;

    private final UserService userService = new UserService();

    public void initialize() {
        passwordField.setVisible(false);
        passwordField.setManaged(false);
        submitButton.setText("Check Username");
        successBtn.setVisible(false);
        successBtn.setManaged(false);
    }

    @FXML
    protected void onClickCheckUsername() {
        if (!usernameField.getText().isEmpty()) {
            String username = usernameField.getText().trim();
            try {
                User user = userService.fetchUserDetailPasswordCreate(username);

                if (user != null) {
                    passwordField.setManaged(true);
                    passwordField.setVisible(true);
                    submitButton.setText("Submit");
                    submitButton.setOnAction(event -> onClickSubmitNewPassword());
                    System.out.println(user.getId() + user.getHashPassword());
                } else {
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "Invalid OR User already registered. If you need help resetting your password, please partner with IT department.");
                }
            } catch (UserException e) {
                AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Invalid User. Unable to retrieve information.");
                throw new RuntimeException(e);
            }
            System.out.println("PRESSED!!!!");
        } else {
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Blank Field", null, "Username field must not be empty.");
        }

    }

    protected void onClickSubmitNewPassword() {

        if (!passwordField.getText().isEmpty()) {
            String username = usernameField.getText().trim();
            String password = null;
            try {
                password = PasswordService.encrypt(passwordField.getText());
            } catch (Exception e) {
                AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Unable to encrypt password.");
                throw new RuntimeException(e);
            }

            User user = new User(username, password);

            try {
                userService.updateUser(user);
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Password created for user: " + username);

                usernameField.setDisable(true);
                passwordField.setDisable(true);
                successBtn.setVisible(true);
                successBtn.setManaged(true);
                submitButton.setVisible(false);
                submitButton.setManaged(false);
            } catch (UserException e) {
                AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Unable to create password");
                throw new RuntimeException(e);
            }
        }
    }
}

