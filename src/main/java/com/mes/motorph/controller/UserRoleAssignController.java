package com.mes.motorph.controller;

import com.mes.motorph.entity.Role;
import com.mes.motorph.entity.User;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.RoleException;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.exception.UserRoleException;
import com.mes.motorph.services.RoleService;
import com.mes.motorph.services.UserRoleService;
import com.mes.motorph.services.UserService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserRoleAssignController {
    @FXML
    private Label breadCrumb;
    @FXML
    private MFXPaginatedTableView<UserRole> userTableView;
    @FXML
    private MFXComboBox<Role> roleComboBox;
    private int empId;
    private UserRoleService userRoleService = new UserRoleService();
    private UserService userService = new UserService();
    private RoleService roleService = new RoleService();

    @FXML
    protected void initialize() {
        setupTable();
        populateRolesComboBox();
        breadCrumb.setText("Administration / Assign / Role");
    }

    public void setupTable() {
        // Clear existing columns
        userTableView.getTableColumns().clear();
//        MFXTableColumn<UserRole> idColumn = new MFXTableColumn<>("Employee ID", true, Comparator.comparing(UserRole::getEmpID));
        MFXTableColumn<UserRole> empNameColumn = new MFXTableColumn<>("Employee Name", true, Comparator.comparing(UserRole::getEmpName));
        MFXTableColumn<UserRole> usernameColumn = new MFXTableColumn<>("Username", true, Comparator.comparing(UserRole::getUsername));
        MFXTableColumn<UserRole> roleColumn = new MFXTableColumn<>("Roles", true, Comparator.comparing(UserRole::getRoles));

//        idColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getEmpID));
        empNameColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getEmpName));
        usernameColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getUsername));
        roleColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getRoles));

//        idColumn.setMaxWidth(0);
        empNameColumn.setMinWidth(150);
        usernameColumn.setMinWidth(200);
        roleColumn.setMinWidth(300);

        userTableView.getTableColumns().addAll( empNameColumn, usernameColumn, roleColumn);

        userTableView.getFilters().addAll(
                new IntegerFilter<>("Employee ID", UserRole::getEmpID),
                new StringFilter<>("Email", UserRole::getEmail)
        );

        try {
            List<UserRole> userRoleList = userRoleService.fetchUsersView();
            userTableView.getItems().clear(); // Clear existing items
            userTableView.setItems(FXCollections.observableArrayList(userRoleList));
        } catch (UserRoleException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    // Method to fetch roles from the repository and populate the ComboBox
    private void populateRolesComboBox() {
        // Fetch roles from the repository
        List<Role> roles = null;
        try {
            roles = roleService.fetchAllRoles();
        } catch (RoleException e) {
            throw new RuntimeException(e);
        }
        // Clear existing items in the ComboBox
        roleComboBox.getItems().clear();
        // Create RoleItem objects containing both ID and title, and add them to the ComboBox
        for (Role role : roles) {
            Role roleItem = new Role(role.getRoleId(), role.getName());
            roleComboBox.getItems().add(roleItem);
        }
         if (!roles.isEmpty()) {
             roleComboBox.getSelectionModel().selectFirst(); // Select the first role by default
         }
    }
    private void selectedEmployee() {
        UserRole selectedUser = userTableView.getSelectionModel().getSelectedValues().get(empId);
        if (selectedUser != null) {
            int empID = selectedUser.getEmpID();
            System.out.println("Selected employee ID: " + empID);

            Role selectedRoleItem = roleComboBox.getValue();
            if (selectedRoleItem != null) {
                int roleId = selectedRoleItem.getRoleId();

                System.out.println("Selected role ID: " + roleId);

                UserRole userRole = new UserRole(empID, roleId);

                // Now you can use the roleId as needed, for example, assign it to the userRole
                try {
                    userRoleService.createNewUserRole(userRole);
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "Role assigned to employee.");
                } catch (UserRoleException e) {
                    throw new RuntimeException(e);
//                    AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Unable to add role.");
                }
            } else {
                System.out.println("No role selected.");
            }
        }
    }

    @FXML
    protected void onClickSubmit() {
        selectedEmployee();
        initialize();
    }

    @FXML
    protected void onClickResetPass() {
        UserRole selectedUser = userTableView.getSelectionModel().getSelectedValues().get(empId);
        if (selectedUser != null) {
            int empID = selectedUser.getEmpID();
            String username = selectedUser.getUsername();
            String password = "";
            System.out.println("Selected employee ID: " + empID + " Username: " + username + " password:  " + password);

            User user = new User(username, password);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to reset password for " + username + " ?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    userService.updateUser(user);
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "Successfully reset password for user: " + username);
                } catch (UserException e) {
                    AlertUtility.showAlert(Alert.AlertType.ERROR, "Information", null, "Unable to reset password for user: " + username);
                    throw new RuntimeException(e);
                }
            }
        } else {
           AlertUtility.showAlert(Alert.AlertType.WARNING, "Information", null, "No selected row.");
        }
    }
}



