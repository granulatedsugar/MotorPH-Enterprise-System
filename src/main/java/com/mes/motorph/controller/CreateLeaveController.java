package com.mes.motorph.controller;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.entity.LeaveRequest;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.exception.LeaveRequestException;
import com.mes.motorph.services.EmployeeService;
import com.mes.motorph.services.LeaveRequestService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class CreateLeaveController {
    @FXML
    private Label breadCrumb;

    @FXML
    private MFXTextField empIdField;

    @FXML
    private MFXTextField empNameField;

    @FXML
    private MFXComboBox leaveTypeComBox;
    @FXML
    private MFXDatePicker startDatePicker;
    @FXML
    private MFXDatePicker endDatePicker;
    LeaveRequestService leaveRequestService = new LeaveRequestService();

    EmployeeService employeeService = new EmployeeService();

    @FXML
    protected void initialize(){
        breadCrumb.setText("File Leave");
        setComboBox();
    }

    private void setComboBox(){
        String[] leavetypes = {"Sick Leave", "Vacation Leave" };
        ObservableList<String> leaveList = FXCollections.observableArrayList();
        for(int i =0; i<leavetypes.length; i++){
           leaveList.add(leavetypes[i]);
        }
        leaveTypeComBox.setItems(leaveList);

    }

    @FXML
    protected void onClickSubmitBtn(){

        if(checkDate()){
            AlertUtility.showAlert(Alert.AlertType.WARNING, "", null, "Select a valid date");
            resetFields();
        }else{
            String leavetype = leaveTypeComBox.getText();
            int employeeId = Integer.parseInt(empIdField.getText());
            Date startDate = Date.valueOf(startDatePicker.getValue());
            Date endDate = Date.valueOf(endDatePicker.getValue());
            Date regDate = Date.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            String status = "Pending";
            try {
               Employee employee = employeeService.fetchEmployeeDetails(employeeId);
                double sickHours = employee.getSickHours();
                double vacaHours = employee.getVacationHours();
                switch (leavetype){
                    case "Sick Leave":
                        long daysBetween = (endDate.getTime() - startDate.getTime());
                        double convertHours = ((TimeUnit.DAYS.convert(daysBetween, TimeUnit.MILLISECONDS)+1)*8); //*8 to hours
                        System.out.println("Convert days: " + convertHours + " Sick Hours: " + sickHours);
                        if(convertHours > sickHours){
                            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Leave Request", null, "Don't have enough sick leave credits");
                            resetFields();
                        }else{
                            LeaveRequest leaveRequest = new LeaveRequest(employeeId, regDate, leavetype, startDate, endDate, status, null);
                            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Leave Request", null, "Leave Request Filed!");
                            leaveRequestService.createLeaveRequest(leaveRequest);
                            resetFields();
                        }
                        break;
                    case "Vacation Leave":
                        daysBetween = (endDate.getTime() - startDate.getTime());
                        convertHours = ((TimeUnit.DAYS.convert(daysBetween, TimeUnit.MILLISECONDS)+1)*8); //*8 to hours
                        if(convertHours > vacaHours){
                            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Leave Request", null, "Don't have enough vacation leave credits");
                            resetFields();
                        }else{
                            LeaveRequest leaveRequest = new LeaveRequest(employeeId, regDate, leavetype, startDate, endDate, status, null);
                            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Leave Request", null, "Leave Request Filed!");
                            leaveRequestService.createLeaveRequest(leaveRequest);
                            resetFields();
                        }
                        break;
                }

            } catch (EmployeeException | LeaveRequestException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private boolean checkDate(){
        boolean isBefore;
        LocalDate selectedDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if(endDate.isBefore(selectedDate)){
            isBefore = true;
        }else{
            isBefore = false;
        }
        return isBefore;

    }

    private void resetFields(){
        empIdField.setText("");
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        leaveTypeComBox.setValue("");
        setComboBox();
    }

}
