package com.mes.motorph.controller;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceEmployeeController {
    private String attendanceId;
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

    AttendanceService attendanceService = new AttendanceService();

    public void setAttendanceId(String attendanceId){
        this.attendanceId = attendanceId;
        attendanceIdField.setText(attendanceId);

        try{
            Attendance attendanceData = attendanceService.fetchAttendanceDataById(attendanceId);
            setAttendanceDetails(attendanceData);
        }catch(AttendanceException e){
            e.printStackTrace();
        }

    }
    private void setAttendanceDetails(Attendance attendance){
        empIdField.setText(String.valueOf(attendance.getEmployeeId()));
        dateField.setValue(LocalDate.parse(String.valueOf(attendance.getDate())));
        timeInField.setText(String.valueOf(LocalTime.parse(String.valueOf(attendance.getTimeIn()))));
        timeOutField.setText(String.valueOf(LocalTime.parse(String.valueOf(attendance.getTimeOut()))));

    }

}
