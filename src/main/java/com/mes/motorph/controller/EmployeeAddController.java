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
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeAddController {

    @FXML
    private Label sceneTitle;
    @FXML
    private Label empIdLabel;
    @FXML
    private TextField firstNameAdd;
    @FXML
    private TextField lastNameAdd;
    @FXML
    private TextField addressAdd;
    @FXML
    private TextField emailAdd;
    @FXML
    private TextField phoneNumAdd;
    @FXML
    private TextField clothingAllowanceAdd;
    @FXML
    private TextField phoneAllowanceAdd;
    @FXML
    private TextField riceSubAdd;
    @FXML
    private TextField pagIbigAdd;
    @FXML
    private TextField philHealthAdd;
    @FXML
    private TextField sssAdd;
    @FXML
    private TextField tinAdd;
    @FXML
    private TextField supervisorAdd;
    @FXML
    private TextField statusAdd;
    @FXML
    private TextField basicSalaryAdd;
    @FXML
    private TextField grossSemiMonthlyRateAdd;
    @FXML
    private TextField hourlyRateAdd;
    @FXML
    private TextField vacationHoursAdd;
    @FXML
    private TextField sickHoursAdd;
    @FXML
    private DatePicker dobAdd;
    @FXML
    private ComboBox<Position> positionAdd;
    @FXML
    private ComboBox<Department> departmentAdd;
    @FXML
    private Button employeeAdd;
    @FXML
    private Button employeeUpdate;

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

    protected void departmentComboBox() throws DepartmentException {
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
        if (!checkFields()) {
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
            int positionId = positionAdd.getSelectionModel().getSelectedItem().getPositionId();
            int departmentId = departmentAdd.getSelectionModel().getSelectedItem().getDeptId();

            Employee employee = new Employee(address, basicSalary, clothingAllowance, dob, email, firstName, lastName, grossSemiMonthlyRate, hourlyRate, pagIbigId, philHealthId, phoneAllowance, phoneNumber, riceSubsidy, sssId, status, supervisor, tinId, vacationHours, sickHours, positionId, departmentId);

            User user = new User(email);
            employeeService.createNewEmployee(employee);
            userService.createNewUser(user);
            resetForm();
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Employee added successfully");
        } else {
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please check if there are any empty fields");
        }
    }



    private Employee employeeAddChecker() {
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
            int positionId = positionAdd.getSelectionModel().getSelectedItem().getPositionId();
            int departmentId = departmentAdd.getSelectionModel().getSelectedItem().getDeptId();

            employee = new Employee(empId, address, basicSalary, clothingAllowance, dob, email, firstName, lastName, grossSemiMonthlyRate, hourlyRate, pagIbigId, philHealthId, phoneAllowance, phoneNumber, riceSubsidy, sssId, status, supervisor, tinId, vacationHours, sickHours, positionId, departmentId);

        return employee;
    }

    private boolean checkFields() {
        if (firstNameAdd.getText().isEmpty() || lastNameAdd.getText().isEmpty() || dobAdd.getValue() == null || addressAdd.getText().isEmpty() || emailAdd.getText().isEmpty() || phoneNumAdd.getText().isEmpty() || clothingAllowanceAdd.getText().isEmpty() || phoneAllowanceAdd.getText().isEmpty() || riceSubAdd.getText().isEmpty() || pagIbigAdd.getText().isEmpty() || philHealthAdd.getText().isEmpty() || sssAdd.getText().isEmpty() || tinAdd.getText().isEmpty() || supervisorAdd.getText().isEmpty() || statusAdd.getText().isEmpty() || basicSalaryAdd.getText().isEmpty() || grossSemiMonthlyRateAdd.getText().isEmpty() || hourlyRateAdd.getText().isEmpty() || vacationHoursAdd.getText().isEmpty() || sickHoursAdd.getText().isEmpty() || positionAdd.getValue() == null || departmentAdd.getValue() == null) {
            return true;
        } else {
            return false;
        }

    }


    @FXML
    protected void onClickUpdate() throws EmployeeException {
        employeeService.updateEmployee(employeeAddChecker());
    }

    public void employeeUpdate(Employee employee) throws PositionException, DepartmentException {
        emailAdd.setDisable(true);
        dobAdd.setDisable(true);
        pagIbigAdd.setDisable(true);
        philHealthAdd.setDisable(true);
        sssAdd.setDisable(true);
        tinAdd.setDisable(true);
        employeeAdd.setVisible(false);

        setEmployeeDataFields(employee);
    }

    private void setEmployeeDataFields(Employee employee) throws PositionException, DepartmentException {
        String empId = String.valueOf(employee.getId());
        sceneTitle.setText("Update Employee " + employee.getFirstName() + " " + employee.getLastName());
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
}

