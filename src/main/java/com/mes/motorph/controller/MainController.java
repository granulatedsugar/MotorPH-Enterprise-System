package com.mes.motorph.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainView;
    @FXML
    private Label welcomeText;
    @FXML
    private Label nikkoLabel;


    @FXML
    protected void onClickEmployees() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/com/mes/motorph/employees-view.fxml"));
            AnchorPane employeesView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            employeesView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(employeesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void onClickPayroll() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/com/mes/motorph/payroll-view.fxml"));
            AnchorPane accountingView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            accountingView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(accountingView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickAttendance() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/com/mes/motorph/attendance-view.fxml"));
            AnchorPane attendanceView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            attendanceView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(attendanceView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}