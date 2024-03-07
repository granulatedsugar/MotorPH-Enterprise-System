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
        //we set the fields
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

        if(hasAttendance()){  //Check if has attendance
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "Attendance Already Exist. Check Record");
        }else{
            if(empIdField.getText().isEmpty()|| dateField.getValue() == null || timeInField.getText().isEmpty() || timeOutField.getText().isEmpty()){// Check if all fields are null then display a message
                AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "Missing Fields");
            }else{
                //if not null get all values from fields
                this.employeeId = Integer.parseInt(empIdField.getText());
                this.date = Date.valueOf(dateField.getValue());
                this.timeIn = Time.valueOf(timeInField.getText());
                this.timeOut = Time.valueOf(timeOutField.getText());
                Attendance attendance = new Attendance(employeeId,date,timeIn,timeOut);
                try {
                    //add attendance
                    attendanceService.createAttendance(attendance);
                    resetFields();
                    //display a message
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, " ", null, "Attendance Created!");
                } catch (AttendanceException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    //create a method that resets all fields
    private void resetFields(){
        empIdField.setText("");
        dateField.setValue(LocalDate.parse(date.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        //set timeInField to 08:00:00
        timeInField.setText("08:00:00");
        //set timeOutField to 5:00:00
        timeOutField.setText("17:00:00");
        timeInBtn.setDisable(false);
        timeOutBtn.setDisable(false);

    }

    @FXML
    private void onClickUpdateCreateAttendance(){
        this.attendanceId = Integer.parseInt(attendanceIdField.getText());
        this.employeeId = Integer.parseInt(empIdField.getText());
        this.date = Date.valueOf(dateField.getValue());
        //get time in and time out, and format it
        Time timeIn = Time.valueOf(LocalTime.parse(timeInField.getText()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        //convert from Time to SQL.Time
        java.sql.Time convertedTimeIn = new java.sql.Time(timeIn.getTime());
        Time timeOut = Time.valueOf(LocalTime.parse(timeOutField.getText()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        java.sql.Time convertedTimeOut = new java.sql.Time(timeOut.getTime());
        //instantiate a new Attendance
        Attendance attendance = new Attendance(attendanceId, employeeId, date, convertedTimeIn,convertedTimeOut);
        try {
            //update attendance
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
            AlertUtility.showAlert(Alert.AlertType.WARNING, "WARNING!", null, "You are currently logged in.");
        }else{
            attendanceService.createAttendance(logIn);
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Time In", null, "Time in logged!");
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
            AlertUtility.showAlert(Alert.AlertType.WARNING, "WARNING!", null, "You have already been logged out.");
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
