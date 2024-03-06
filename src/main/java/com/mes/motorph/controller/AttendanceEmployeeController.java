package com.mes.motorph.controller;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import com.mes.motorph.utils.AlertUtility;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceEmployeeController {

    @FXML
    private Label breadCrumb;
    @FXML
    private Label sceneTitle;
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

    @FXML
    protected void initialize() throws AttendanceException, SQLException {
        breadCrumb.setText("Attendance / Create");
        sceneTitle.setText("Create New Attendance");
        this.date = Date.valueOf(LocalDate.now());
        dateField.setValue(LocalDate.parse(date.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        addBtn.setVisible(false);
        updateBtn.setVisible(false);
    }

    public void setData(Employee employee) {
        breadCrumb.setText("Attendance / Time In/Out");
        sceneTitle.setText("Time In/Out");
        empIdField.setDisable(true);
        empIdField.setText(String.valueOf(employee.getId()));

    }

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
        updateBtn.setVisible(true);
        dateField.setDisable(false);

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
    private void onClickAddCreateAttendance() throws AttendanceException, SQLException {
        if(hasAttendance()){
            System.out.println("ALREADY EXIST!");
        }else{
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


    }

    //create a method that resets everything
    private void resetFields(){
        empIdField.setText("");
        dateField.setValue(null);
        timeInField.setText("08:00:00");
        timeOutField.setText("17:00:00");
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
    private void onClickTimeInBtn() throws AttendanceException, SQLException {
        Time timeIn = Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        java.sql.Time convertedTimeIn = new java.sql.Time(timeIn.getTime());
        timeInField.setText(String.valueOf(convertedTimeIn));
        int employeeId = Integer.parseInt(empIdField.getText());
        Date date = Date.valueOf(dateField.getValue());
        Attendance logIn = new Attendance(employeeId, date, convertedTimeIn, null);
        if(hasAttendance()){
            AlertUtility.showAlert(Alert.AlertType.WARNING, "WARNING!", null, "DUPLICATE! CLICK TIMEOUT!");
        }else{
            attendanceService.createAttendance(logIn);
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Time In", null, "TIME IN LOGGED!");
            timeInBtn.setDisable(true);
        }

    }

    private Attendance attendanceRecord() throws AttendanceException, SQLException {
       Date refDate = Date.valueOf(dateField.getValue());
       int employeeId = Integer.parseInt(empIdField.getText());
       Attendance attendance = attendanceService.fetchAttendanceRecordByDate(employeeId, refDate);
       return attendance;
    }
    private boolean hasAttendance() throws AttendanceException, SQLException {
        Attendance attendance = attendanceRecord();
        if(attendance == null){
            return false;
        }else{
            return true;
        }
    }

    @FXML
    private void onClickTimeOutBtn() throws AttendanceException, SQLException {
        Time timeOut = Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        java.sql.Time convertedTimeOut = new java.sql.Time(timeOut.getTime());
        timeOutField.setText(String.valueOf(convertedTimeOut));

        int id = attendanceRecord().getId();
        int employeeId = attendanceRecord().getEmployeeId();
        Date date = attendanceRecord().getDate();
        Time timeIn = attendanceRecord().getTimeIn();
        if(hasAttendance() && attendanceRecord().getTimeOut() != null){
            AlertUtility.showAlert(Alert.AlertType.WARNING, "WARNING!", null, "ALREADY TIME OUT");
        }else{
            Attendance attendance = new Attendance(id, employeeId, date, timeIn, convertedTimeOut);
            attendanceService.updateAttendance(attendance);
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Time Out", null, "GREAT WORK! TIME OUT");
            timeOutBtn.setDisable(true);
            
        }
    }

    public void onClickAddToCreate(){
        empIdField.setDisable(false);
        addBtn.setVisible(true);
        timeInField.setDisable(false);
        timeInField.setText("08:00:00");
        timeOutField.setText("17:00:00");
        timeOutField.setDisable(false);
        timeInBtn.setVisible(false);
        timeOutBtn.setVisible(false);
        dateField.setDisable(false);
    }


}
