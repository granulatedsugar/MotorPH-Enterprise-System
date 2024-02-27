package com.mes.motorph.controller;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.services.EmployeeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class EmployeeController {

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableColumn<Employee, String> firstNameField;

    @FXML
    private TableColumn<Employee, String> lastNameField;

    @FXML
    private TableColumn<Employee, String> emailField;

    @FXML
    private TableColumn<Employee, String> contactNumField;

    @FXML
    private TableColumn<Employee, String> supervisorField;

    @FXML
    private TableColumn<Employee, String> positionField;

    @FXML
    private TableColumn<Employee, String> deptField;

    EmployeeService employeeService = new EmployeeService();

    @FXML
    protected void initialize() {
        firstNameField.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameField.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailField.setCellValueFactory(new PropertyValueFactory<>("Email"));
        contactNumField.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        supervisorField.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
        positionField.setCellValueFactory(new PropertyValueFactory<>("positionId"));
        deptField.setCellValueFactory(new PropertyValueFactory<>("deptId"));

        try {
            List<Employee> employees = employeeService.fetchAllEmployees();
            ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList(employees);
            employeeTableView.setItems(employeeObservableList);

        } catch (EmployeeException e) {
            e.printStackTrace();
        }

    }



}
