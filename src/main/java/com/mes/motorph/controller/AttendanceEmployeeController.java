package com.mes.motorph.controller;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import com.mes.motorph.utils.AlertUtility;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceEmployeeController {
    private int attendanceId;
    private int employeeId;
    private Date date;
    private Time timeIn;
    private Time timeOut;

    @FXML
    private TextField attendanceIdField;
    @FXML
    private TextField empIdField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField timeInField;
    @FXML
    private TextField timeOutField;
    @FXML
    private Button updateBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button timeInBtn;
    @FXML
    private Button timeOutBtn;


    AttendanceService attendanceService = new AttendanceService();

    public void setAttendanceDetails(int attendanceId, int employeeId, Date date, Time timeIn, Time timeOut){
        this.attendanceId = attendanceId;
        this.employeeId = employeeId;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;

        attendanceIdField.setText(String.valueOf(attendanceId));
        timeInBtn.setVisible(false);
        timeOutBtn.setVisible(false);
        timeInField.setDisable(false);
        timeOutField.setDisable(false);
        addBtn.setVisible(false);
        empIdField.setDisable(true);

        Attendance attendance = new Attendance(attendanceId,employeeId,date,timeIn,timeOut);
        setAttendanceDetailsFields(attendance);

    }
    private void setAttendanceDetailsFields(Attendance attendance){
        empIdField.setText(String.valueOf(attendance.getEmployeeId()));
        dateField.setValue(LocalDate.parse(String.valueOf(attendance.getDate())));
        timeInField.setText((LocalTime.parse(String.valueOf(attendance.getTimeIn())).format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        timeOutField.setText((LocalTime.parse(String.valueOf(attendance.getTimeOut())).format(DateTimeFormatter.ofPattern("HH:mm:ss"))));

    }

    @FXML
    private void onClickAddCreateAttendance(){
      if(empIdField.getText().isEmpty()|| dateField.getValue() == null || timeInField.getText().isEmpty() || timeOutField.getText().isEmpty()){
          AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "Missing Fields");
      }else{
          this.employeeId = Integer.parseInt(empIdField.getText());
          this.date = Date.valueOf(dateField.getValue());
          this.timeIn = Time.valueOf(timeInField.getText());
          this.timeOut = Time.valueOf(timeOutField.getText());
          Attendance attendance = new Attendance(employeeId,date,timeIn,timeOut);
          try {
              attendanceService.createAttendance(attendance);
              resetFields();
              AlertUtility.showAlert(Alert.AlertType.INFORMATION, " ", null, "Attendance Created!");
          } catch (AttendanceException e) {
              throw new RuntimeException(e);
          }
      }

    }

    //create a method that resets everything
    private void resetFields(){
        empIdField.setText(null);
        dateField.setValue(null);
        timeInField.setText(null);
        timeOutField.setText(null);
        timeInBtn.setDisable(false);
        timeOutBtn.setDisable(false);

    }

    @FXML
    private void onClickUpdateCreateAttendance(){
        this.attendanceId = Integer.parseInt(attendanceIdField.getText());
        this.employeeId = Integer.parseInt(empIdField.getText());
        this.date = Date.valueOf(dateField.getValue());
        Time timeIn = Time.valueOf(LocalTime.parse(timeInField.getText()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        java.sql.Time convertedTimeIn = new java.sql.Time(timeIn.getTime());
        Time timeOut = Time.valueOf(LocalTime.parse(timeOutField.getText()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        java.sql.Time convertedTimeOut = new java.sql.Time(timeOut.getTime());
        Attendance attendance = new Attendance(attendanceId, employeeId, date, convertedTimeIn,convertedTimeOut);
        try {
            attendanceService.updateAttendance(attendance);
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, " ", null, "Attendance Updated!");
        } catch (AttendanceException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void onClickTimeInBtn(){
        Time timeIn  = Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        java.sql.Time convertedTimeIn = new java.sql.Time(timeIn.getTime());
        timeInField.setText(String.valueOf(convertedTimeIn));

    }

    @FXML
    private void onClickTimeOutBtn(){
        Time timeOut = Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        java.sql.Time convertedTimeOut = new java.sql.Time(timeOut.getTime());
        timeOutField.setText(String.valueOf(convertedTimeOut));

    }

    @FXML
    private void onTimeInMouseClicked(){
        int mouseclicked =+ 1;
        if(mouseclicked == 1){
            timeInBtn.setDisable(true);
        }
    }

    @FXML
    private void onTimeOutMouseClicked(){
        int mouseclicked =+ 1;
        if(mouseclicked == 1){
            timeOutBtn.setDisable(true);
        }

    }

}
