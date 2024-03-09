package com.mes.motorph.controller;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.DepartmentException;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.services.DepartmentService;
import com.mes.motorph.services.EmployeeService;
import com.mes.motorph.services.PositionService;
import com.mes.motorph.services.UserService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class EmployeeController {

    @FXML
    private FilteredList<Employee> filteredEmployees;
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
    private TextField employeeSearch;
    @FXML
    private TableColumn<Employee, String> positionField;
    @FXML
    private TableColumn<Employee, String> deptField;
    @FXML
    private MFXPaginatedTableView<Employee> employeeTableView;

    private int id;

    EmployeeService employeeService = new EmployeeService();
    UserService userService = new UserService();
    PositionService positionService = new PositionService();
    DepartmentService departmentService = new DepartmentService();

    @FXML
    protected void initialize() {
        setupTable();
        employeeTableView.autosizeColumnsOnInitialization();
        employeeTableView.currentPageProperty().addListener((observable, oldValue, newValue) -> employeeTableView.autosizeColumns());
    }

    private void setupTable() {
        employeeTableView.getTableColumns().clear();

        MFXTableColumn<Employee> firstNameColumn = new MFXTableColumn<>("First Name", true, Comparator.comparing(Employee::getFirstName));
        MFXTableColumn<Employee> lastNameColumn = new MFXTableColumn<>("Last Name", true, Comparator.comparing(Employee::getLastName));
        MFXTableColumn<Employee> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(Employee::getEmail));
        MFXTableColumn<Employee> contactNumColumn = new MFXTableColumn<>("Contact Number", true, Comparator.comparing(Employee::getPhoneNumber));
        MFXTableColumn<Employee> supervisorColumn = new MFXTableColumn<>("Immediate Supervisor", true, Comparator.comparing(Employee::getSupervisor));

        MFXTableColumn<Employee> updateButton = new MFXTableColumn<>("", true, Comparator.comparing(Employee::getId));
        MFXTableColumn<Employee> deleteButton = new MFXTableColumn<>("", true, Comparator.comparing(Employee::getId));

        firstNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getFirstName));
        lastNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getLastName));
        emailColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getEmail));
        contactNumColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getPhoneNumber));
        supervisorColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getSupervisor));

        updateButton.setRowCellFactory(employee -> new MFXTableRowCell<>(rowEmployee -> "") {
            {
                updateButton.setAlignment(Pos.CENTER);
                updateButton.setMinWidth(62);
                updateButton.setMaxWidth(62);
                updateButton.setColumnResizable(false);

                MFXButton button = createButton("🖊", "mfx-button-table-update", event -> onClickUpdate());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });
        deleteButton.setRowCellFactory(employee -> new MFXTableRowCell<>(rowEmployee -> "") {
            {
                deleteButton.setAlignment(Pos.CENTER);
                deleteButton.setMinWidth(62);
                deleteButton.setMaxWidth(62);
                deleteButton.setColumnResizable(false);

                MFXButton button = createButton("⛔", "mfx-button-table-delete", event -> onClickDelete());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        employeeTableView.getTableColumns().addAll(firstNameColumn, lastNameColumn, emailColumn, contactNumColumn, supervisorColumn, deleteButton, updateButton);

        employeeTableView.getFilters().addAll(
                new StringFilter<>("First Name", Employee::getFirstName),
                new StringFilter<>("Last Name", Employee::getLastName),
                new StringFilter<>("Email", Employee::getEmail)
        );

        try {
            List<Employee> employeeList = employeeService.fetchAllEmployees();
            employeeTableView.getItems().clear();
            employeeTableView.setItems(FXCollections.observableArrayList(employeeList));
        } catch (EmployeeException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
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
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedValues().get(id);
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
                    employeeService.deleteEmployee(employeeId);
                    initialize();
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "", null, "Employee Deleted");
                }catch (EmployeeException | UserException e){
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
                }
            }else{
                // Do nothing
            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
        }


    }

    @FXML
    protected void onClickUpdate(){
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedValues().get(id);

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

        Employee employee = new Employee(id, address, basicSalary, clothingAllowance, dob, email, firstName, lastName, grossSemiMonthlyRate, hourlyRate, pagIbigId, philHealthId, phoneAllowance, phoneNumber, riceSubsidy, sssId, status, supervisor, tinId, vacationHours, sickHours, positionId, departmentId);

        navigateToAddView(employee);

    }

    protected void navigateToAddView(Employee employee) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/employee-add-view.fxml"));

        try {
            Parent employeeAddView = loader.load();
            EmployeeAddController employeeAddController = loader.getController();

            employeeAddController.employeeUpdate(employee);

            BorderPane mainView = (BorderPane) employeeTableView.getScene().getRoot().lookup("#mainView");

            mainView.setCenter(employeeAddView);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PositionException e) {
            throw new RuntimeException(e);
        } catch (DepartmentException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onClickSearchEmployee() {
        String employeeFirstName = employeeSearch.getText().trim();

        try {
            Predicate<Employee> filterPredicate = employee -> employee.getFirstName().equals(employeeFirstName);

            filteredEmployees.setPredicate(filterPredicate);

            // Check if any records match the employee ID
            if (filteredEmployees.isEmpty()) {
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "No records found for the provided Employee Name.");
            }
        } catch (NumberFormatException e) {
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid Employee Name.");
        }
    }
    // Method to create and configure a button
    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }
}
