package com.mes.motorph.view;

import com.mes.motorph.entity.UserRole;
import com.mes.motorph.services.PasswordService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
    private Button employeeSection;
    @FXML
    private Button employeeDetailsBtn;
    @FXML
    private Button attendanceSection;
    @FXML
    private Button timeInOutBtn;
    @FXML
    private Button attendanceManageBtn;
    @FXML
    private Button payrollSection;
    @FXML
    private Button payrollStatementBtn;
    @FXML
    private Button payrollCreateBtn;
    @FXML
    private Button adminSection;
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


    private List<UserRole> userRoles;

    @FXML
    protected void initialize() {
        // Initialization code, if needed
        setDashboardData();
        //setDashboardButtons();


    }

    public void setDashboardData() {
        accountMenu.setText("Name");
    }

    // Method to initialize user roles
    public void initData(List<UserRole> userRoles) {
        this.userRoles = userRoles;
        // Perform any additional initialization based on user roles
        System.out.println(userRoles);
        // For example, hide/show buttons or menu items based on user roles
        boolean hasAccessToAdminDashboard = false;
        boolean hasAccessToEmployees = false;
        Set<Integer> adminRoleIds = new HashSet<>(Arrays.asList(1, 2)); // Assuming roles 1, 2, and 3 grant access to the dashboard
        for (UserRole role : userRoles) {
            if (adminRoleIds.contains(role.getRoleId())) {
                hasAccessToAdminDashboard = true;
                hasAccessToEmployees = true;
                break; // No need to continue looping if both conditions are met
            } else if (role.getRoleId() == 3) {
                hasAccessToEmployees = true;
            }
        }
        overviewLabel.setVisible(hasAccessToAdminDashboard);
        overviewLabel.setManaged(hasAccessToAdminDashboard);
        dashboardBtn.setVisible(hasAccessToAdminDashboard);
        dashboardBtn.setManaged(hasAccessToAdminDashboard);
        managementLabel.setVisible(hasAccessToAdminDashboard);
        managementLabel.setManaged(hasAccessToAdminDashboard);
        employeeSection.setVisible(hasAccessToAdminDashboard);
        employeeSection.setManaged(hasAccessToAdminDashboard);
        employeeDetailsBtn.setVisible(hasAccessToAdminDashboard);
        employeeDetailsBtn.setManaged(hasAccessToAdminDashboard);
        attendanceManageBtn.setVisible(hasAccessToAdminDashboard);
        attendanceManageBtn.setManaged(hasAccessToAdminDashboard);
        payrollSection.setVisible(hasAccessToAdminDashboard);
        payrollSection.setManaged(hasAccessToAdminDashboard);
        payrollStatementBtn.setVisible(hasAccessToAdminDashboard);
        payrollStatementBtn.setManaged(hasAccessToAdminDashboard);
        payrollCreateBtn.setVisible(hasAccessToAdminDashboard);
        payrollCreateBtn.setManaged(hasAccessToAdminDashboard);
        adminSection.setVisible(hasAccessToAdminDashboard);
        adminSection.setManaged(hasAccessToAdminDashboard);
        userBtn.setVisible(hasAccessToAdminDashboard);
        userBtn.setManaged(hasAccessToAdminDashboard);
        roleBtn.setVisible(hasAccessToAdminDashboard);
        roleBtn.setManaged(hasAccessToAdminDashboard);
        positionBtn.setVisible(hasAccessToAdminDashboard);
        positionBtn.setManaged(hasAccessToAdminDashboard);
        departmentBtn.setVisible(hasAccessToAdminDashboard);
        departmentBtn.setManaged(hasAccessToAdminDashboard);
    }

    @FXML
    protected void onClickDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/dashboard-view.fxml"));
            AnchorPane dashboard = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            dashboard.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(dashboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickEmployees() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/employee-view.fxml"));
            AnchorPane employeesView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            employeesView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(employeesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickAttendance() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-view.fxml"));
            AnchorPane attendanceView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            attendanceView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(attendanceView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickPayroll() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/payroll-list-view.fxml"));
            AnchorPane accountingView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            accountingView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(accountingView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickCreatePayroll() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/payroll-create-view.fxml"));
            AnchorPane accountingView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            accountingView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(accountingView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickTimeInOut() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-employee-view.fxml"));
            AnchorPane timeInOutView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            timeInOutView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(timeInOutView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: @Sid - whats the plan for this?
    @FXML
    protected void onClickAttendanceReport() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-report.fxml"));
            AnchorPane attendanceView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            attendanceView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(attendanceView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickUsers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/user-list-view.fxml"));
            AnchorPane usersView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            usersView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(usersView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickRole() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/role-list-view.fxml"));
            AnchorPane rolesView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            rolesView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(rolesView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    @FXML
    protected void onClickPosition() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/position-view.fxml"));
            AnchorPane positionView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            positionView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(positionView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickDepartment(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/department-view.fxml"));
            AnchorPane departmentView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            departmentView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(departmentView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onClickUserRole(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/user-role-list-view.fxml"));
            AnchorPane userRoleView = (AnchorPane) fxmlLoader.load(); // Assuming it's an AnchorPane

            // Get reference to existing BorderPane:
            BorderPane borderPane = (BorderPane) mainView.getScene().getRoot(); // Update "mainView" with your actual BorderPane instance

            // Set visibility to true (optional if not already visible):
            userRoleView.setVisible(true);

            // Add the loaded AnchorPane to the center region:
            borderPane.setCenter(userRoleView);

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

}