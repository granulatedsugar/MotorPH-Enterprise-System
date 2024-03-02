package com.mes.motorph.controller;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.entity.Department;
import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.exception.DepartmentException;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.services.DepartmentService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class DepartmentController {
    @FXML
    private TableView<Department> departmentTableView;
    @FXML
    private TableColumn<Department, Integer> departmentIdColumn;
    @FXML
    private TableColumn<Department,String> departmentTitleColumn;
    @FXML
    private TextField deptField;


    DepartmentService departmentService = new DepartmentService();

    @FXML
    protected void initialize(){
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("deptId"));
        departmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("deptDesc"));

        try{
            List<Department> departments = departmentService.fetchDepartments();
            ObservableList<Department> departmentObservableList = FXCollections.observableArrayList(departments);
            departmentTableView.setItems(departmentObservableList);
        } catch (DepartmentException e) {
            throw new RuntimeException(e);
        }

    }



    @FXML
    private void onClickAdd() throws Exception {
        if(deptField.getText().isEmpty()){
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Update Error", null, "Field is Empty");
        }
        else{
            inputProcess(true);
        }
    }

    @FXML
    private void onClickUpdate() throws Exception {
        Department selectedIndex = departmentTableView.getSelectionModel().getSelectedItem();
        if(selectedIndex != null){
            inputProcess(false);
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to update");
        }


    }

    @FXML
    private void onClickDelete() throws DepartmentException{
        Department department = departmentTableView.getSelectionModel().getSelectedItem();
        if(department != null){
            int deptId = department.getDeptId();
            System.out.println(deptId);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to delete this row?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton,cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButton){
                try{
                    departmentService.deleteDepartment(deptId);
                    initialize();
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "", null, "Row Deleted");
                }catch (DepartmentException e){
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
                }
            }else{

            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
        }

    }

    private Department fetchDepartment(){
        String deptDesc = deptField.getText();
        return new Department(deptDesc);
    }

    protected void inputProcess(boolean isNew) throws Exception{
        try {
            Department department = fetchDepartment();
            if(isNew) {
                departmentService.createDepartment(department);
                initialize();
                resetField();
            } else{
                int id = departmentTableView.getSelectionModel().getSelectedItem().getDeptId();
                departmentService.updateDepartment(String.valueOf(department), id);
                initialize();
                resetField();
            }
        }catch (DepartmentException e) {
                String action = isNew ? "creating" : "updating";
                String position = fetchDepartment().getDeptDesc();
                String errorMessage = "error " + action + " the" + position + "| Reason: " + e.getMessage();
                AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, errorMessage);
        }
    }

    private void resetField(){
        deptField.setText("");
    }

}
