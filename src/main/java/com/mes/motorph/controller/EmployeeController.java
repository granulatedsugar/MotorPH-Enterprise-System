package com.mes.motorph.controller;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.services.EmployeeService;
import com.mes.motorph.services.UserService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
    UserService userService = new UserService();

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

    @FXML
    protected void onClickAdd(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/employee-add-view.fxml"));

        try {
            Parent employeeAddView = loader.load();
            EmployeeAddController employeeAddController = loader.getController();

            // Get the main BorderPane from your main view
            BorderPane mainView = (BorderPane) employeeTableView.getScene().getRoot().lookup("#mainView");

            // Replace the center content of the main BorderPane with the payroll-create-view
            mainView.setCenter(employeeAddView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickDelete(){
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if(selectedEmployee != null){
            int employeeId = selectedEmployee.getId();
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
                    userService.deleteUser(selectedEmployee.getEmail());
                    employeeService.deleteEmployee(selectedEmployee.getId());
                    initialize();
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "", null, "Row Deleted");
                }catch (EmployeeException | UserException e){
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
                }
            }else{

            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
        }


    }

    @FXML
    protected void onClickUpdate(){
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();

        int id = selectedEmployee.getId();
        String firstName = selectedEmployee.getFirstName();
        String lastName = selectedEmployee.getLastName();
        Date dob = selectedEmployee.getDateOfBirth();
        String address = selectedEmployee.getAddress();
        String email = selectedEmployee.getEmail();
        String phoneNumber = selectedEmployee.getPhoneNumber();
        double clothingAllowance = selectedEmployee.getClothingAllowance();
        double phoneAllowance = selectedEmployee.getPhoneAllowance();
        double riceSubsidy = selectedEmployee.getRiceSubsidy();
        String pagIbigId = selectedEmployee.getPagIbig();
        String philHealthId = selectedEmployee.getPhilHealth();
        String sssId = selectedEmployee.getSss();
        String tinId = selectedEmployee.getTin();
        String supervisor = selectedEmployee.getSupervisor();
        String status = selectedEmployee.getStatus();
        double basicSalary = selectedEmployee.getBaseSalary();
        double grossSemiMonthlyRate = selectedEmployee.getGrossSemiMonthlyRate();
        double hourlyRate = selectedEmployee.getHourlyRate();
        double vacationHours = selectedEmployee.getVacationHours();
        double sickHours = selectedEmployee.getSickHours();
        int positionId = selectedEmployee.getPositionId();
        int departmentId = selectedEmployee.getDeptId();

        navigateToAddView(id, address, basicSalary, clothingAllowance, dob, email, firstName, grossSemiMonthlyRate, hourlyRate, lastName, pagIbigId, philHealthId, phoneAllowance, phoneNumber, riceSubsidy, sssId, status, supervisor, tinId, vacationHours, sickHours, positionId, departmentId);


    }

    protected void navigateToAddView(int id, String address, double baseSalary, double clothingAllowance, Date dateOfBirth, String email, String firstName, double grossSemiMonthlyRate, double hourlyRate, String lastName, String pagIbig, String philHealth, double phoneAllowance, String phoneNumber, double riceSubsidy, String sss, String status, String supervisor, String tin, double vacationHours, double sickHours, int positionId, int deptId) {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/employee-add-view.fxml"));

        try {
            Parent employeeAddView = loader.load();
            EmployeeAddController employeeAddController = loader.getController();

            employeeAddController.employeeUpdate(id, address, baseSalary, clothingAllowance, dateOfBirth, email, firstName, grossSemiMonthlyRate, hourlyRate, lastName, pagIbig, philHealth, phoneAllowance, phoneNumber, riceSubsidy, sss, status, supervisor, tin, vacationHours, sickHours, positionId, deptId);

            BorderPane mainView = (BorderPane) employeeTableView.getScene().getRoot().lookup("#mainView");

            mainView.setCenter(employeeAddView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
