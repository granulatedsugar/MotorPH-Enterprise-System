package com.mes.motorph.controller;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.entity.LeaveRequest;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.exception.LeaveRequestException;
import com.mes.motorph.services.EmployeeService;
import com.mes.motorph.services.LeaveRequestService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeaveRequestController {

    private int employeeId;
    @FXML
    private MFXPaginatedTableView<LeaveRequest> leaveReqTableView;
    @FXML
    private MFXComboBox leaveTypeComboBox;
    @FXML
    private MFXDatePicker startDatePicker;
    @FXML
    private MFXDatePicker endDatePicker;
    @FXML
    private MFXTextField empNumberField;
    @FXML
    private Label breadCrumb;


    LeaveRequestService leaveRequestService = new LeaveRequestService();
    EmployeeService employeeService = new EmployeeService();

    @FXML
    protected void initialize() {
        breadCrumb.setText("Attendance / Leave Request");
    }

    public void setData(int empId){
        this.employeeId = empId;
        empNumberField.setText(String.valueOf(empId));
        empNumberField.setDisable(true);
        System.out.println(empId);
        setupTable();
        setComboBox();
    }


    private void setupTable() {
        leaveReqTableView.getTableColumns().clear();

        MFXTableColumn<LeaveRequest> regDate = new MFXTableColumn<>("Submitted Date", true, Comparator.comparing(LeaveRequest::getRegDate));
        MFXTableColumn<LeaveRequest> startDate = new MFXTableColumn<>("Start Date", true, Comparator.comparing(LeaveRequest::getStartDate));
        MFXTableColumn<LeaveRequest> endDate = new MFXTableColumn<>("End Date", true, Comparator.comparing(LeaveRequest::getEndDate));
        MFXTableColumn<LeaveRequest> leaveType = new MFXTableColumn<>("Leave Type", true, Comparator.comparing(LeaveRequest::getLeaveType));
        MFXTableColumn<LeaveRequest> apprDate = new MFXTableColumn<>("Status Date", true, Comparator.comparing(LeaveRequest::getApproveDate));
        MFXTableColumn<LeaveRequest> status = new MFXTableColumn<>("Status", true, Comparator.comparing(LeaveRequest::getStatus));

        regDate.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getRegDate));
        leaveType.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getLeaveType));
        startDate.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getStartDate));
        endDate.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getEndDate));
        apprDate.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getApproveDate));
        status.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getStatus));

        leaveReqTableView.getTableColumns().addAll(regDate,leaveType,startDate,endDate,apprDate,status);
        leaveReqTableView.getFilters().addAll(
                new StringFilter<>("status", LeaveRequest::getStatus)
        );

        try{
            System.out.println("THIS IS INSIDE" + employeeId);
            List<LeaveRequest> leaveRequest = leaveRequestService.fetchAllLeaveRequestByEmpId(employeeId);
            leaveReqTableView.getItems().clear();
            leaveReqTableView.setItems(FXCollections.observableArrayList(leaveRequest));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void setComboBox(){
        String[] leavetypes = {"Vacation Leave"};
        ObservableList<String> leaveList = FXCollections.observableArrayList();
        for(int i =0; i<leavetypes.length; i++){
            leaveList.add(leavetypes[i]);
        }
        leaveTypeComboBox.setItems(leaveList);

    }

    @FXML
    private void onClickSubmitBtn(){
        int employeeId = Integer.parseInt(empNumberField.getText());
        Date regDate = Date.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Date startDate = Date.valueOf(startDatePicker.getValue());
        Date endDate = Date.valueOf(endDatePicker.getValue());
        String leavetype = leaveTypeComboBox.getText();
        String status = "Pending";
        if(checkDate()){
            resetFields();
        }else {
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
                        }else{
                            LeaveRequest leaveRequest = new LeaveRequest(employeeId, regDate, leavetype, startDate, endDate, status, null);
                            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Leave Request", null, "Leave Request Filed!");
                            leaveRequestService.createLeaveRequest(leaveRequest);
                            setupTable();
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
                            setupTable();
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
        LocalDate today = LocalDate.now();
        if(selectedDate != null && selectedDate.isBefore(today) || endDate.isBefore(selectedDate)){
            isBefore = true;
            AlertUtility.showAlert(Alert.AlertType.WARNING, "", null, "Select a valid date");
        }else{
            isBefore = false;

        }
        return isBefore;

    }

    private void resetFields(){
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        leaveTypeComboBox.setValue("Vacation Leave");
        setComboBox();
    }



}
