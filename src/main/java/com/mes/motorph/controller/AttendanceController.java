package com.mes.motorph.controller;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import com.mes.motorph.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private AnchorPane newView;

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
    private void onClickUpdateAttendance() throws AttendanceException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-employee-view.fxml"));
            AnchorPane attendanceView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) newView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            attendanceView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(attendanceView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
