package com.mes.motorph.controller;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import com.mes.motorph.utils.AlertUtility;
import com.mes.motorph.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

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

    private AttendanceService attendanceService = new AttendanceService();

    @FXML
    protected void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));

        try{
            List<Attendance> attendances = attendanceService.fetchAttedance();
            ObservableList<Attendance> attendanceObservableList = FXCollections.observableArrayList(attendances);
            attendanceTableView.setItems(attendanceObservableList);
        }catch (AttendanceException e){
            e.printStackTrace();
        }
    }
    @FXML
    protected void onClickDelete() throws AttendanceException{
        attendanceService.deleteAttendance(attendanceTableView.getSelectionModel().getSelectedItem().getId());

    }

    @FXML
    private void onClickUpdateAttendance() throws AttendanceException {
        Attendance selectedAttendance = attendanceTableView.getSelectionModel().getSelectedItem();

        if(selectedAttendance != null){
            String attendanceId = String.valueOf(selectedAttendance.getId());
            navigateToAttendanceEmployee(attendanceId);
            System.out.println(attendanceId);
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to update");
        }

    }
    private void navigateToAttendanceEmployee(String attendanceId){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/attendance-employee-view.fxml"));

        try{
            Parent attendanceEmployeeView = loader.load();
            AttendanceEmployeeController attendanceEmployeeController = loader.getController();
            attendanceEmployeeController.setAttendanceId(attendanceId);

            BorderPane mainView = (BorderPane) attendanceTableView.getScene().getRoot().lookup("#mainView");

            mainView.setCenter(attendanceEmployeeView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
