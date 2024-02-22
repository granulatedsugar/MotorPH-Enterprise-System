package com.mes.motorph.utils;

import javafx.scene.control.Alert;

// Utility
public class AlertUtility {
    public static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

//    Polymorphism
//    public static void showAlert(AlertType alertType, String title, String header, String content) {
//        Alert alert = createAlert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(header);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//
//    private static Alert createAlert(AlertType alertType) {
//        switch (alertType) {
//            case INFORMATION:
//                return new InformationAlert();
//            case WARNING:
//                return new WarningAlert();
//            case ERROR:
//                return new ErrorAlert();
//            default:
//                return new Alert(Alert.AlertType.NONE);
//        }
//    }
//
//    public enum AlertType {
//        INFORMATION,
//        WARNING,
//        ERROR
//    }
//
//    private static class InformationAlert extends Alert {
//        public InformationAlert() {
//            super(AlertType.INFORMATION);
//        }
//    }
//
//    private static class WarningAlert extends Alert {
//        public WarningAlert() {
//            super(AlertType.WARNING);
//        }
//    }
//
//    private static class ErrorAlert extends Alert {
//        public ErrorAlert() {
//            super(AlertType.ERROR);
//        }
//    }
}
