package com.mes.motorph.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ViewFactory {
    @FXML
    private BorderPane mainView;

    @FXML
    protected void onClickDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/dashboard-view.fxml"));
            AnchorPane dashboard = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            dashboard.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(dashboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickEmployees() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/employee-view.fxml"));
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
    protected void onClickAttendance() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-view.fxml"));
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

    @FXML
    protected void onClickPayroll() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/payroll-list-view.fxml"));
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
    protected void onClickCreatePayroll() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/payroll-create-view.fxml"));
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
    protected void onClickAttendanceReport() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-report.fxml"));
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

    @FXML
    protected void onClickUsers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/user-list-view.fxml"));
            AnchorPane usersView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            usersView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(usersView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickPosition() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/position-view.fxml"));
            AnchorPane positionView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            positionView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(positionView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}