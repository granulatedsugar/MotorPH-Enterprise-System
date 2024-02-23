package com.mes.motorph.controller;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

}
