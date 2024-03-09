package com.mes.motorph.controller;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.services.LeaveRequestService;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CreateLeaveController {
    @FXML
    private Label breadCrumb;

    @FXML
    private MFXTextField empIdField;

    @FXML
    private MFXTextField empNameField;

    @FXML
    private MFXComboBox leaveTypeComBox;
    LeaveRequestService leaveRequestService = new LeaveRequestService();

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
        String leavetype = leaveTypeComBox.getText();
        System.out.println(leavetype);
    }




    public void setData(Employee employee){
        empIdField.setText(String.valueOf(employee.getId()));
        empNameField.setText(employee.getFirstName() +" "+employee.getLastName());

    }
    //change to leaverequest


}
