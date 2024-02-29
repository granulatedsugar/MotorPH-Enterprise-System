package com.mes.motorph.controller;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import com.mes.motorph.utils.AlertUtility;
import com.mes.motorph.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Time;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AttendanceController {

    @FXML
    private TableView<Attendance> attendanceTableView;
    @FXML
    private TableColumn<Attendance, Integer> idColumn;
    @FXML
    private TableColumn<Attendance, Integer> empIdColumn;
    @FXML
    private TableColumn<Attendance, Date> dateColumn;
    @FXML
    private TableColumn<Attendance, Time> timeInColumn;
    @FXML
    private TableColumn<Attendance, Time> timeOutColumn;
    @FXML
    private FilteredList<Attendance> filteredList;
    @FXML
    private TextField empIdField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private AttendanceService attendanceService = new AttendanceService();

    @FXML
    protected void initialize(){
        setupContextMenu();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(column -> new TableCell<Attendance, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item,empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    setText(item.toString());
                }

                 }
             }
        );
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));


        try{
            List<Attendance> attendances = attendanceService.fetchAttedance();
            if(attendances.isEmpty()){
                AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "No Attendance Record");
            }else{
                ObservableList<Attendance> attendanceObservableList = FXCollections.observableArrayList(attendances);
                filteredList = new FilteredList<>(attendanceObservableList);
                attendanceTableView.setItems(filteredList);

                empIdField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    if(newValue.isEmpty()){
                        filteredList.setPredicate(null);
                    }
                } );
                //Datepicker
                startDatePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                    if(newValue == null){
                        filteredList.setPredicate(null);
                    }else{
                        filteredList.setPredicate(attendance -> {
                          String selectedStartDate = newValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                          return selectedStartDate.equals(attendance.getDate().toString());
                        });
                    }
                });
            }
        }catch (AttendanceException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickSearchEmpId(){
        String employeeIdText = empIdField.getText().trim();

        try{
            int employeeId = Integer.parseInt(employeeIdText);
            Predicate<Attendance> filteredPredicate = attendance -> attendance.getEmployeeId() == employeeId;

            filteredList.setPredicate(filteredPredicate);

            if(filteredList.isEmpty()){
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "No records found for the provided Employee ID.");
            }
        }catch (NumberFormatException e){
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid Employee ID.");
        }


    }
    @FXML
    protected void onClickDelete() throws AttendanceException{
        Attendance selectedAttendance = attendanceTableView.getSelectionModel().getSelectedItem();
        if(selectedAttendance != null){
            int attendanceId = selectedAttendance.getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to delete this row?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton,cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButton){
                try{
                    attendanceService.deleteAttendance(attendanceId);
                    initialize();
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "", null, "Row Deleted");
                }catch (AttendanceException e){
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
                }
            }else{
                System.out.println("CANCEL!");
            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
        }

    }

    @FXML
    private void onClickUpdateAttendance() throws AttendanceException {
        Attendance selectedAttendance = attendanceTableView.getSelectionModel().getSelectedItem();

        if(selectedAttendance != null){
            int attendanceId = selectedAttendance.getId();
            int employeedId = selectedAttendance.getEmployeeId();
            Date date = selectedAttendance.getDate();
            Time timeIn = selectedAttendance.getTimeIn();
            Time timeOut = selectedAttendance.getTimeOut();
            navigateToAttendanceEmployee(attendanceId, employeedId, date, timeIn, timeOut);
            System.out.println(attendanceId);
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to update");
        }

    }
    private void navigateToAttendanceEmployee(int attendanceId, int employeeId, Date date, Time timeIn, Time timeOut){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/attendance-employee-view.fxml"));

        try{
            Parent attendanceEmployeeView = loader.load();
            AttendanceEmployeeController attendanceEmployeeController = loader.getController();
            attendanceEmployeeController.setAttendanceDetails(attendanceId,employeeId,date,timeIn,timeOut);

            BorderPane mainView = (BorderPane) attendanceTableView.getScene().getRoot().lookup("#mainView");

            mainView.setCenter(attendanceEmployeeView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickAddManageAttendance(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/attendance-employee-view.fxml"));
        try{
            Parent attendanceEmployeeView = loader.load();
            AttendanceEmployeeController attendanceEmployeeController = loader.getController();

            BorderPane mainView = (BorderPane) attendanceTableView.getScene().getRoot().lookup("#mainView");

            mainView.setCenter(attendanceEmployeeView);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void showContextMenu(MouseEvent event, TableRow<Attendance> row, Attendance rowData){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem updateMenu = new MenuItem("Update");
        updateMenu.setOnAction(e -> {
            int attendanceId = rowData.getId();
            int employeeId = rowData.getEmployeeId();
            Date date = rowData.getDate();
            Time timeIn = rowData.getTimeIn();
            Time timeOut = rowData.getTimeOut();
            navigateToAttendanceEmployee(attendanceId, employeeId,date,timeIn,timeOut);
        });

        MenuItem deleteMenu = new MenuItem("Delete");
        deleteMenu.setOnAction(e -> {
            int attendanceId = rowData.getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to delete this row?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton,cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButton){
                try{
                    attendanceService.deleteAttendance(attendanceId);
                    initialize();
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "", null, "Row Deleted");
                }catch (AttendanceException ex){
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
                }
            }else{
                System.out.println("CANCEL!");
            }
    });
        contextMenu.getItems().addAll(updateMenu,deleteMenu);
        contextMenu.show(row, event.getScreenX(), event.getScreenY());
    }

    private void setupContextMenu(){
        attendanceTableView.setRowFactory(attendanceTableView1 -> {
            TableRow tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY && !tableRow.isEmpty()) {
                    Attendance rowData = (Attendance) tableRow.getItem();
                    showContextMenu(mouseEvent, tableRow, rowData);
                }
            });
            return tableRow;
        });
    }


}
