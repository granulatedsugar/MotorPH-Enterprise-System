package com.mes.motorph.view;

import com.mes.motorph.Main;
import com.mes.motorph.controller.AttendanceEmployeeController;
import com.mes.motorph.controller.EmployeeAddController;
import com.mes.motorph.entity.Employee;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.DepartmentException;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.services.EmployeeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewFactory {
    @FXML
    private BorderPane mainView;
    @FXML
    private Label overviewLabel;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Label managementLabel;
    @FXML
    private Text employeeSection;
    @FXML
    private Button employeeDetailsBtn;
    @FXML
    private Button employeeCreateBtn;
    @FXML
    private Text attendanceSection;
    @FXML
    private Button timeInOutBtn;
    @FXML
    private Button attendanceManageBtn;
    @FXML
    private Text payrollSection;
    @FXML
    private Button payrollStatementBtn;
    @FXML
    private Button payrollCreateBtn;
    @FXML
    private Text adminSection;
    @FXML
    private Button userBtn;
    @FXML
    private Button roleBtn;
    @FXML
    private Button positionBtn;
    @FXML
    private Button departmentBtn;
    @FXML
    private MenuButton accountMenu;
    @FXML
    private MenuItem profileMenuItem;
    @FXML
    private MenuItem logoutMenuItem;
    @FXML
    private Text welcomeTitle;
    @FXML
    private Text welcomeSub;
    @FXML
    private Text welcomeText;
    private int employeeId;
    private String username;
    private int roleId;
    private List<UserRole> userRoles;
    private EmployeeService employeeService = new EmployeeService();
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    protected void initialize() {
        // Initialization code, if needed
        setDashboardButtons();
    }

    // Method to initialize user roles
    public void initData(List<UserRole> userRoles) throws EmployeeException {
        this.userRoles = userRoles;
        // Extract employee ID, username, and role ID from userRoles
        if (!userRoles.isEmpty()) {
            UserRole firstUserRole = userRoles.get(0); // Assuming the user only has one role
            this.employeeId = firstUserRole.getId();
            this.username = firstUserRole.getUsername();
            this.roleId = firstUserRole.getRoleId();
        }

        setDashboardData();
        // Perform any additional initialization based on user roles
        System.out.println(userRoles);
        // For example, hide/show buttons or menu items based on user roles
        boolean hasDashboardAccess = false;
        boolean hasManagementAccess = false;
        boolean hasEmployeeSectionAcess = false;
        boolean hasEmployeeDetailsAccess = false;
        boolean hasEmployeeAddAccess = false;
        boolean hasAttendanceManageAccess = false;
        boolean hasPayrollSectionAccess = false;
        boolean hasPayrollStatementAccess = false;
        boolean hasPayrollCreateAccess = false;
        boolean hasAdminSectionAccess = false;
        boolean hasUserAccess = false;
        boolean hasRoleAccess = false;
        boolean hasPositionAccess = false;
        boolean hasDepartmentAccess = false;

        Set<Integer> executiveRoles = new HashSet<>(Arrays.asList(2, 3)); // Assuming roles 1, 2, and 3 grant access to the dashboard
        for (UserRole role : userRoles) {
            if (executiveRoles.contains(role.getRoleId())) {
                hasDashboardAccess = true;
                hasManagementAccess = true;
                hasEmployeeSectionAcess = true;
                hasEmployeeAddAccess = true;
                hasEmployeeDetailsAccess = true;
                hasAttendanceManageAccess = true;
                hasPayrollSectionAccess = true;
                hasPayrollStatementAccess = true;
                hasPayrollCreateAccess = true;
                break; // No need to continue looping if both conditions are met
            } else if (role.getRoleId() == 5) { // Accounting
                hasManagementAccess = true;
                hasPayrollSectionAccess = true;
                hasPayrollStatementAccess = true;
                hasPayrollCreateAccess = true;
            } else if (role.getRoleId() == 6) { // Human Resources
                hasManagementAccess = true;
                hasAttendanceManageAccess = true;
                hasEmployeeSectionAcess = true;
                hasEmployeeDetailsAccess = true;
                hasEmployeeAddAccess = true;
                hasAdminSectionAccess = true;
                hasPositionAccess = true;
                hasDepartmentAccess = true;
            } else if (role.getRoleId() == 9) { // IT
                hasAdminSectionAccess = true;
                hasUserAccess = true;
                hasRoleAccess = true;
                hasPositionAccess = true;
                hasDepartmentAccess = true;
            } else if (role.getRoleId() == 1) { // ADMIN
                hasDashboardAccess = true;
                hasManagementAccess = true;
                hasEmployeeSectionAcess = true;
                hasEmployeeAddAccess = true;
                hasEmployeeDetailsAccess = true;
                hasAttendanceManageAccess = true;
                hasPayrollSectionAccess = true;
                hasPayrollStatementAccess = true;
                hasPayrollCreateAccess = true;
                hasAdminSectionAccess = true;
                hasUserAccess = true;
                hasRoleAccess = true;
                hasPositionAccess = true;
                hasDepartmentAccess = true;
            }
        }
        overviewLabel.setVisible(hasDashboardAccess);
        overviewLabel.setManaged(hasDashboardAccess);
        dashboardBtn.setVisible(hasDashboardAccess);
        dashboardBtn.setManaged(hasDashboardAccess);
        managementLabel.setVisible(hasManagementAccess);
        managementLabel.setManaged(hasManagementAccess);
        employeeSection.setVisible(hasEmployeeSectionAcess);
        employeeSection.setManaged(hasEmployeeSectionAcess);
        employeeCreateBtn.setVisible(hasEmployeeAddAccess);
        employeeCreateBtn.setManaged(hasEmployeeAddAccess);
        employeeDetailsBtn.setVisible(hasEmployeeDetailsAccess);
        employeeDetailsBtn.setManaged(hasEmployeeDetailsAccess);
        attendanceManageBtn.setVisible(hasAttendanceManageAccess);
        attendanceManageBtn.setManaged(hasAttendanceManageAccess);
        payrollSection.setVisible(hasPayrollSectionAccess);
        payrollSection.setManaged(hasPayrollSectionAccess);
        payrollStatementBtn.setVisible(hasPayrollStatementAccess);
        payrollStatementBtn.setManaged(hasPayrollStatementAccess);
        payrollCreateBtn.setVisible(hasPayrollCreateAccess);
        payrollCreateBtn.setManaged(hasPayrollCreateAccess);
        adminSection.setVisible(hasAdminSectionAccess);
        adminSection.setManaged(hasAdminSectionAccess);
        userBtn.setVisible(hasUserAccess);
        userBtn.setManaged(hasUserAccess);
        roleBtn.setVisible(hasRoleAccess);
        roleBtn.setManaged(hasRoleAccess);
        positionBtn.setVisible(hasPositionAccess);
        positionBtn.setManaged(hasPositionAccess);
        departmentBtn.setVisible(hasDepartmentAccess);
        departmentBtn.setManaged(hasDepartmentAccess);
    }

    public void setDashboardData() throws EmployeeException {
        Employee employee = employeeService.fetchEmployeeDetails(employeeId);
        accountMenu.setText(employee.getFirstName());
        welcomeTitle.setText("Welcome back 👋");
        welcomeSub.setText(employee.getFirstName() + " " + employee.getLastName());
        welcomeText.setText("If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything.");
    }

    @FXML
    protected void onClickDashboard() {
        navigateToView("/com/mes/motorph/dashboard-view.fxml");
    }

    @FXML
    protected void onClickEmployees() {
        navigateToView("/com/mes/motorph/employee-view.fxml");
    }

    @FXML
    protected void onClickAddEmployee() {
        navigateToView("/com/mes/motorph/employee-add-view.fxml");
    }

    @FXML
    protected void onClickAttendance() {
        navigateToView("/com/mes/motorph/attendance-view.fxml");
    }

    @FXML
    protected void onClickPayroll() {
        navigateToView("/com/mes/motorph/payroll-list-view.fxml");
    }

    @FXML
    protected void onClickCreatePayroll() {
        navigateToView("/com/mes/motorph/payroll-create-view.fxml");
    }

    @FXML
    protected void onClickTimeInOut() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-employee-view.fxml"));
            AnchorPane timeInOut = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance


            timeInOut.setVisible(true);
            Employee employee = employeeService.fetchEmployeeDetails(employeeId);
            AttendanceEmployeeController attendanceEmployeeController = fxmlLoader.getController();
            attendanceEmployeeController.setData(employee);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(timeInOut);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            throw new RuntimeException(e);
        }
    }

        // TODO: Review if needed @Sid
    @FXML
    protected void onClickAttendanceReport() {
        navigateToView("/com/mes/motorph/attendance-report.fxml");
    }

    @FXML
    protected void onClickUsers() {
        navigateToView("/com/mes/motorph/user-assign-list-view.fxml");
    }

    @FXML
    protected void onClickPosition() {
        navigateToView("/com/mes/motorph/position-list-view.fxml");
    }

    @FXML
    protected void onClickDepartment() {
        navigateToView("/com/mes/motorph/department-view.fxml");
    }

    @FXML
    protected void onClickUserRole(){
        navigateToView("/com/mes/motorph/user-role-list-view.fxml");
    }

    @FXML
    protected void onClickProfile() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/employee-add-view.fxml"));
            AnchorPane profileView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance


            profileView.setVisible(true);
            Employee employee = employeeService.fetchEmployeeDetails(employeeId);
            EmployeeAddController employeeAddController = fxmlLoader.getController();
            employeeAddController.setData(employee);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(profileView);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmployeeException e) {
            throw new RuntimeException(e);
        } catch (PositionException e) {
            throw new RuntimeException(e);
        } catch (DepartmentException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: @Sid missing user roles
    private void navigateToView(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource(fxmlPath));
            AnchorPane view = (AnchorPane) fxmlLoader.load();

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot();

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setDashboardButtons() {
        overviewLabel.setVisible(false);
        overviewLabel.setManaged(false);
        dashboardBtn.setVisible(false);
        dashboardBtn.setManaged(false);
        managementLabel.setVisible(false);
        managementLabel.setManaged(false);
        employeeSection.setVisible(false);
        employeeSection.setManaged(false);
        employeeDetailsBtn.setVisible(false);
        employeeDetailsBtn.setManaged(false);
        attendanceManageBtn.setVisible(false);
        attendanceManageBtn.setManaged(false);
        payrollSection.setVisible(false);
        payrollSection.setManaged(false);
        payrollStatementBtn.setVisible(false);
        payrollStatementBtn.setManaged(false);
        payrollCreateBtn.setVisible(false);
        payrollCreateBtn.setManaged(false);
        adminSection.setVisible(false);
        adminSection.setManaged(false);
        userBtn.setVisible(false);
        userBtn.setManaged(false);
        roleBtn.setVisible(false);
        roleBtn.setManaged(false);
        positionBtn.setVisible(false);
        positionBtn.setManaged(false);
        departmentBtn.setVisible(false);
        departmentBtn.setManaged(false);
    }

    @FXML
    protected void handleLogout() {
        // Close the main stage
        Stage mainStage = (Stage) accountMenu.getScene().getWindow();
        mainStage.close();

        // Open the login stage
       mainApp.openLoginStage();
    }
}
