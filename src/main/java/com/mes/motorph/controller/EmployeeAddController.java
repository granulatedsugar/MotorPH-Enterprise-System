package com.mes.motorph.controller;

import com.mes.motorph.entity.Department;
import com.mes.motorph.entity.Employee;
import com.mes.motorph.entity.Position;
import com.mes.motorph.entity.User;
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
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeAddController {
    @FXML
    private Label breadCrumb;
    @FXML
    private Label sceneTitle;
    @FXML
    private Label empIdLabel;
    @FXML
    private MFXTextField firstNameAdd;
    @FXML
    private MFXTextField lastNameAdd;
    @FXML
    private MFXTextField addressAdd;
    @FXML
    private MFXTextField emailAdd;
    @FXML
    private MFXTextField phoneNumAdd;
    @FXML
    private MFXTextField clothingAllowanceAdd;
    @FXML
    private MFXTextField phoneAllowanceAdd;
    @FXML
    private MFXTextField riceSubAdd;
    @FXML
    private MFXTextField pagIbigAdd;
    @FXML
    private MFXTextField philHealthAdd;
    @FXML
    private MFXTextField sssAdd;
    @FXML
    private MFXTextField tinAdd;
    @FXML
    private MFXTextField supervisorAdd;
    @FXML
    private MFXTextField statusAdd;
    @FXML
    private MFXTextField basicSalaryAdd;
    @FXML
    private MFXTextField grossSemiMonthlyRateAdd;
    @FXML
    private MFXTextField hourlyRateAdd;
    @FXML
    private MFXTextField vacationHoursAdd;
    @FXML
    private MFXTextField sickHoursAdd;
    @FXML
    private MFXDatePicker dobAdd;
    @FXML
    private MFXComboBox<Position> positionAdd;
    @FXML
    private MFXComboBox<Department> departmentAdd;
    @FXML
    private MFXButton employeeAdd;
    @FXML
    private MFXButton employeeUpdate;

    private int id;
    private String firstName;
    private String lastName;
    private Date dob;
    private String address;
    private String email;
    private String phoneNumber;
    private double clothingAllowance;
    private double phoneAllowance;
    private double riceSubsidy;
    private String pagIbigId;
    private String philHealthId;
    private String sssId;
    private String tinId;
    private String supervisor;
    private String status;
    private double basicSalary;
    private double grossSemiMonthlyRate;
    private double hourlyRate;
    private double vacationHours;
    private double sickHours;
    private int positionId;
    private int departmentId;

    //Services
    PositionService positionService = new PositionService();
    DepartmentService departmentService = new DepartmentService();
    EmployeeService employeeService = new EmployeeService();
    UserService userService = new UserService();


    @FXML
    protected void initialize() throws PositionException, DepartmentException {
        positionComboBox();
        departmentComboBox();
        employeeUpdate.setVisible(false);


    }

    public void setData(Employee employee) throws PositionException, DepartmentException {
        setEmployeeDataFields(employee);
        breadCrumb.setText("Employee / Profile / " + employee.getFirstName() + " " + employee.getLastName());
        //sceneTitle.setText("Profile: " + employee.getFirstName() + " " + employee.getLastName());
        employeeAdd.setVisible(false);
        employeeUpdate.setVisible(true);
        disableTextFields();

    }

    protected void positionComboBox() {
        try {
            List<Position> positions = positionService.fetchPositions();
            positionAdd.setPromptText("Select a position");
            // Add items to the ComboBox
            positionAdd.getItems().addAll(positions);
            // Set a listener to handle selection
            positionAdd.setOnAction(event -> {
                Position selectedPosition = positionAdd.getSelectionModel().getSelectedItem();
                System.out.println("Selected Position: " + selectedPosition.getPositionId());
            });
        } catch (PositionException e) {
            e.printStackTrace();
        }
    }

    protected void departmentComboBox() {
        try {
            List<Department> departments = departmentService.fetchDepartments();

            departmentAdd.setPromptText("Select a Department");

            departmentAdd.getItems().addAll((departments));

            departmentAdd.setOnAction(event -> {
                Department selectedDepartment = departmentAdd.getSelectionModel().getSelectedItem();
                System.out.println("Selected Department: " + selectedDepartment.getDeptId() + " - " + selectedDepartment.getDeptDesc());
            });
        } catch (DepartmentException e) {
            e.printStackTrace();
        }

    }

    //Creates new employee
    @FXML
    protected void onClickCreate() throws EmployeeException, UserException {
        if(!checkFields()) {
            String firstName = firstNameAdd.getText();
            String lastName = lastNameAdd.getText();
            Date dob = Date.valueOf(dobAdd.getValue());
            String address = addressAdd.getText();
            String email = emailAdd.getText();
            String phoneNumber = phoneNumAdd.getText();
            double clothingAllowance = parseNonNegativeDouble(clothingAllowanceAdd.getText());
            double phoneAllowance = parseNonNegativeDouble(phoneAllowanceAdd.getText());
            double riceSubsidy = parseNonNegativeDouble(riceSubAdd.getText());
            String pagIbigId = pagIbigAdd.getText();
            String philHealthId = philHealthAdd.getText();
            String sssId = sssAdd.getText();
            String tinId = tinAdd.getText();
            String supervisor = supervisorAdd.getText();
            String status = statusAdd.getText();
            double basicSalary = parseNonNegativeDouble(basicSalaryAdd.getText());
            double grossSemiMonthlyRate = parseNonNegativeDouble(grossSemiMonthlyRateAdd.getText());
            double hourlyRate = parseNonNegativeDouble(hourlyRateAdd.getText());
            double vacationHours = parseNonNegativeDouble(vacationHoursAdd.getText());
            double sickHours = parseNonNegativeDouble(sickHoursAdd.getText());
            int positionId = positionAdd.getSelectionModel().getSelectedItem().getPositionId();
            int departmentId = departmentAdd.getSelectionModel().getSelectedItem().getDeptId();

            Employee employee = new Employee(address, basicSalary, clothingAllowance, dob, email, firstName, lastName, grossSemiMonthlyRate, hourlyRate, pagIbigId, philHealthId, phoneAllowance, phoneNumber, riceSubsidy, sssId, status, supervisor, tinId, vacationHours, sickHours, positionId, departmentId);

            User user = new User(email);
            employeeService.createNewEmployee(employee);
            userService.createNewUser(user);
            resetForm();
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Employee added successfully");
        } else {
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please check if there are any empty or negative fields");
        }
    }

    //checks double values to see if it is a negative value
    private double parseNonNegativeDouble(String value) throws EmployeeException{
        try {
            double parsedValue = Double.parseDouble(value);
            if (parsedValue < 0) {
                AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please check if there are any empty or negative fields");
                throw new EmployeeException("Negative or empty value present in data field");
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            // Handle the case where the value is not a valid non-negative double
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please check if there are any empty or negative fields");
            throw new EmployeeException("Negative or empty value present in data field");
        }
    }

    private Employee employeeAddChecker() throws PositionException, DepartmentException {
        Employee employee = null;

            int empId = Integer.parseInt(empIdLabel.getText());
            String firstName = firstNameAdd.getText();
            String lastName = lastNameAdd.getText();
            Date dob = Date.valueOf(dobAdd.getValue());
            String address = addressAdd.getText();
            String email = emailAdd.getText();
            String phoneNumber = phoneNumAdd.getText();
            double clothingAllowance = Double.parseDouble(clothingAllowanceAdd.getText());
            double phoneAllowance = Double.parseDouble(phoneAllowanceAdd.getText());
            double riceSubsidy = Double.parseDouble(riceSubAdd.getText());
            String pagIbigId = pagIbigAdd.getText();
            String philHealthId = philHealthAdd.getText();
            String sssId = sssAdd.getText();
            String tinId = tinAdd.getText();
            String supervisor = supervisorAdd.getText();
            String status = statusAdd.getText();
            double basicSalary = Double.parseDouble(basicSalaryAdd.getText());
            double grossSemiMonthlyRate = Double.parseDouble(grossSemiMonthlyRateAdd.getText());
            double hourlyRate = Double.parseDouble(hourlyRateAdd.getText());
            double vacationHours = Double.parseDouble(vacationHoursAdd.getText());
            double sickHours = Double.parseDouble(sickHoursAdd.getText());
            String positionDescription = positionAdd.getPromptText();
            String departmentDescription = departmentAdd.getPromptText();
            List<Position> positionList = positionService.fetchPositions();
            List<Department> departmentList = departmentService.fetchDepartments();

            // Retrieve the IDs using the descriptions from the position list
            int positionId = getPositionIdFromDescription(positionDescription, positionList);
            int departmentId = getDepartmentIdFromDescription(departmentDescription, departmentList);

            employee = new Employee(empId, address, basicSalary, clothingAllowance, dob, email, firstName, lastName, grossSemiMonthlyRate, hourlyRate, pagIbigId, philHealthId, phoneAllowance, phoneNumber, riceSubsidy, sssId, status, supervisor, tinId, vacationHours, sickHours, positionId, departmentId);

        return employee;
    }

    // Checks input fields for text/values
    private boolean checkFields() {
        if (firstNameAdd.getText().isEmpty() || lastNameAdd.getText().isEmpty() || dobAdd.getValue() == null || addressAdd.getText().isEmpty() || emailAdd.getText().isEmpty() || phoneNumAdd.getText().isEmpty() || clothingAllowanceAdd.getText().isEmpty() || phoneAllowanceAdd.getText().isEmpty() || riceSubAdd.getText().isEmpty() || pagIbigAdd.getText().isEmpty() || philHealthAdd.getText().isEmpty() || sssAdd.getText().isEmpty() || tinAdd.getText().isEmpty() || supervisorAdd.getText().isEmpty() || statusAdd.getText().isEmpty() || basicSalaryAdd.getText().isEmpty() || grossSemiMonthlyRateAdd.getText().isEmpty() || hourlyRateAdd.getText().isEmpty() || vacationHoursAdd.getText().isEmpty() || sickHoursAdd.getText().isEmpty() || positionAdd.getValue() == null || departmentAdd.getValue() == null) {
            return true;
        } else {
            return false;
        }

    }

    // Update employee button
    @FXML
    protected void onClickUpdate() throws EmployeeException, PositionException, DepartmentException {
        if (!updateCheckFields()) {
            employeeService.updateEmployee(employeeAddChecker());
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "Updated Employee.");
        } else {
            // Show warning message for empty or negative fields
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please check if there are any empty or negative fields");
            throw new EmployeeException("Negative or empty value present in data field");
        }
    }

    //THIS IS A TEST REMOVE IF NOT WORKING TY
    // Method to check if any field contains negative value
    private boolean updateCheckFields() throws EmployeeException {
        try {
            // Check each double field for negative values
            double clothingAllowance = parseNonNegativeDouble(clothingAllowanceAdd.getText());
            double phoneAllowance = parseNonNegativeDouble(phoneAllowanceAdd.getText());
            double riceSubsidy = parseNonNegativeDouble(riceSubAdd.getText());
            double basicSalary = parseNonNegativeDouble(basicSalaryAdd.getText());
            double grossSemiMonthlyRate = parseNonNegativeDouble(grossSemiMonthlyRateAdd.getText());
            double hourlyRate = parseNonNegativeDouble(hourlyRateAdd.getText());
            double vacationHours = parseNonNegativeDouble(vacationHoursAdd.getText());
            double sickHours = parseNonNegativeDouble(sickHoursAdd.getText());

            // If any field contains a negative value, return true
            if (clothingAllowance < 0 || phoneAllowance < 0 || riceSubsidy < 0 || basicSalary < 0 ||
                    grossSemiMonthlyRate < 0 || hourlyRate < 0 || vacationHours < 0 || sickHours < 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            // Handle the case where a field contains an invalid double value
            return true;
        }
        // All fields are non-negative
        return false;
    }

    // Puts certain fields to disabled/not visible state & populates employee data fields
    public void employeeUpdate(Employee employee) throws PositionException, DepartmentException {
        breadCrumb.setText("Employee / Update / " + employee.getFirstName() + " " + employee.getLastName());
        emailAdd.setDisable(true);
        dobAdd.setDisable(true);
        pagIbigAdd.setDisable(true);
        philHealthAdd.setDisable(true);
        sssAdd.setDisable(true);
        tinAdd.setDisable(true);
        employeeAdd.setVisible(false);
        employeeUpdate.setVisible(true);

        setEmployeeDataFields(employee);
    }

    // Sets up data fields when updating an employee
    private void setEmployeeDataFields(Employee employee) throws PositionException, DepartmentException {
        String empId = String.valueOf(employee.getId());
        //sceneTitle.setText("Update Employee " + employee.getFirstName() + " " + employee.getLastName());
        empIdLabel.setText(empId);
        firstNameAdd.setText(employee.getFirstName());
        lastNameAdd.setText(employee.getLastName());
        dobAdd.setValue(LocalDate.parse(String.valueOf(employee.getDateOfBirth())));
        addressAdd.setText(employee.getAddress());
        emailAdd.setText(employee.getEmail());
        phoneNumAdd.setText(employee.getPhoneNumber());
        clothingAllowanceAdd.setText(String.valueOf(employee.getClothingAllowance()));
        phoneAllowanceAdd.setText(String.valueOf(employee.getPhoneAllowance()));
        riceSubAdd.setText(String.valueOf(employee.getRiceSubsidy()));
        pagIbigAdd.setText(employee.getPagIbig());
        philHealthAdd.setText(employee.getPhilHealth());
        sssAdd.setText(employee.getSss());
        tinAdd.setText(employee.getTin());
        supervisorAdd.setText(employee.getSupervisor());
        statusAdd.setText(employee.getStatus());
        basicSalaryAdd.setText(String.valueOf(employee.getBaseSalary()));
        grossSemiMonthlyRateAdd.setText(String.valueOf(employee.getGrossSemiMonthlyRate()));
        hourlyRateAdd.setText(String.valueOf(employee.getHourlyRate()));
        vacationHoursAdd.setText(String.valueOf(employee.getVacationHours()));
        sickHoursAdd.setText(String.valueOf(employee.getSickHours()));
        positionAdd.setPromptText(String.valueOf(positionService.fetchPositionDescription(employee.getPositionId())));
        departmentAdd.setPromptText(String.valueOf(departmentService.fetchDepartmentDescription(employee.getDeptId())));
    }

    // Method to reset form
    protected void resetForm() {
        firstNameAdd.setText("");
        lastNameAdd.setText("");
        dobAdd.setValue(null);
        addressAdd.setText("");
        emailAdd.setText("");
        phoneNumAdd.setText("");
        clothingAllowanceAdd.setText("");
        phoneAllowanceAdd.setText("");
        riceSubAdd.setText("");
        pagIbigAdd.setText("");
        philHealthAdd.setText("");
        sssAdd.setText("");
        tinAdd.setText("");
        supervisorAdd.setText("");
        statusAdd.setText("");
        basicSalaryAdd.setText("");
        grossSemiMonthlyRateAdd.setText("");
        hourlyRateAdd.setText("");
        vacationHoursAdd.setText("");
        sickHoursAdd.setText("");
        positionAdd.setPromptText("Select a position");
        departmentAdd.setPromptText("Select a Department");

    }

    // Method to get position ID from description using the position list
    private int getPositionIdFromDescription(String description, List<Position> positionList) {
        for (Position position : positionList) {
            if (position.getTitle().equals(description)) {
                return position.getPositionId();
            }
        }
        return 0; // Return a default value if position is not found
    }

    // Method to get department ID from description using the department service
    private int getDepartmentIdFromDescription(String description, List<Department> departments) {
        for (Department department : departments) {
            if (department.getDeptDesc().equals(description)) {
                return department.getDeptId();
            }
        }
        return 0; // Return a default value if position is not found
    }

    private void disableTextFields() {
        firstNameAdd.setDisable(true);
        lastNameAdd.setDisable(true);
        dobAdd.setDisable(true); // Assuming dobAdd is a DatePicker
        addressAdd.setDisable(true);
        emailAdd.setDisable(true);
        phoneNumAdd.setDisable(true);
        clothingAllowanceAdd.setDisable(true);
        phoneAllowanceAdd.setDisable(true);
        riceSubAdd.setDisable(true);
        pagIbigAdd.setDisable(true);
        philHealthAdd.setDisable(true);
        sssAdd.setDisable(true);
        tinAdd.setDisable(true);
        supervisorAdd.setDisable(true);
        statusAdd.setDisable(true);
        basicSalaryAdd.setDisable(true);
        grossSemiMonthlyRateAdd.setDisable(true);
        hourlyRateAdd.setDisable(true);
        vacationHoursAdd.setDisable(true);
        sickHoursAdd.setDisable(true);
        positionAdd.setDisable(true);
        departmentAdd.setDisable(true);

    }
}

