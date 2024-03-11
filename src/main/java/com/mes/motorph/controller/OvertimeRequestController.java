package com.mes.motorph.controller;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.entity.Overtime;
import com.mes.motorph.exception.OvertimeException;
import com.mes.motorph.services.OvertimeService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OvertimeRequestController {

    @FXML
    private Label breadCrumb;
    @FXML
    private MFXTextField empIdField;
    @FXML
    private MFXDatePicker dateField;

    OvertimeService overtimeService = new OvertimeService();


    @FXML
    protected void initialize(){
        breadCrumb.setText("Attendance / File Overtime");
    }


    @FXML
    protected void onClickSubmitBtn(){
        Date today = Date.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        if(dateField.getValue() != null && !dateField.getValue().isBefore(today.toLocalDate())){
            Date selectedDate = Date.valueOf(dateField.getValue());
            int employeeId = Integer.parseInt(empIdField.getText());
            String status = "Pending";

            Overtime overtime = new Overtime(selectedDate, employeeId, status);
            try {
                overtimeService.createOvertime(overtime);
                resetFields();
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Overtime", null, "Overtime Request Filed.");
            } catch (OvertimeException e) {
                throw new RuntimeException(e);
            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Overtime", null, "Select a valid date");
            resetFields();
        }


    }

    private void resetFields(){
        dateField.setValue(null);
    }



    public void setData(Employee employee){
        empIdField.setText(String.valueOf(employee.getId()));
        empIdField.setDisable(true);
    }
}
