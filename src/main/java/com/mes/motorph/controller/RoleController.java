package com.mes.motorph.controller;

import com.mes.motorph.entity.Role;
import com.mes.motorph.entity.User;
import com.mes.motorph.exception.RoleException;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.services.RoleService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class RoleController {
    @FXML
    private Label breadCrumb;
    @FXML
    private Text sceneTitle;
    @FXML
    private TextField roleSearchField;
    @FXML
    private TableView<Role> rolesTableView;
    @FXML
    private TableColumn<Role, Integer> roleIdColumn;
    @FXML
    private TableColumn<Role, String> roleNameColumn;
    @FXML
    private TextField roleField;
    @FXML
    private TextField roleIdField;
    private FilteredList filteredRoles;


    private RoleService roleService = new RoleService();

    @FXML
    protected void initialize() throws RoleException {
        breadCrumb.setText("Administation / Manage / Role");
        sceneTitle.setText("Manage Roles");

        setupContextMenu();
        fetchRoles();
    }

    @FXML
    protected void fetchRoles() throws RoleException {
        roleIdColumn.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        roleNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        try {
            List<Role> roles = roleService.fetchAllRoles();

            if (roles.isEmpty()) {
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "No role data found.");
            } else {
                ObservableList<Role> allRoles = FXCollections.observableArrayList(roles);
                filteredRoles = new FilteredList<>(allRoles);
                rolesTableView.setItems(filteredRoles);

                roleSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.isEmpty()) {
                        filteredRoles.setPredicate(null);
                    }
                });
            }
        } catch (RoleException e) {
            throw new RoleException("Error loading table: " + e.getMessage(), e);
        }
    }

    @FXML
    protected void onClickSearchRole() {
        String roleText = roleSearchField.getText().trim();

        try {
            Predicate<Role> filterPredicate = role -> Objects.equals(role.getName(), roleText);

            filteredRoles.setPredicate(filterPredicate);

            if (filteredRoles.isEmpty()) {
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "No records found for provided Role.");
            }
        } catch (Exception e) {
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid role.");
        }
    }

    @FXML
    protected void setRoleDetails(String roleId, String roleName) {
        roleIdField.setText(roleId);
        roleField.setText(roleName);
    }

    private void showContextMenu(MouseEvent event, TableRow<Role> row, Role rowData) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> {
            int roleId = rowData.getRoleId();
            String roleName = rowData.getName();
            setRoleDetails(String.valueOf(roleId), roleName);
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            int roleId = rowData.getRoleId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Role" + roleId + " ?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    roleService.deleteRole(roleId);
                    initialize();
                } catch (RoleException ex) {
                    // Handle exception
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
                }
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
        });
        contextMenu.getItems().addAll(editItem, deleteItem);
        // Show the context menu at the mouse cursor's location
        contextMenu.show(row, event.getScreenX(), event.getScreenY());
    }

    private void setupContextMenu() {
        rolesTableView.setRowFactory(tv -> {
            TableRow<Role> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    Role rowData = row.getItem();
                    showContextMenu(event, row, rowData);
                }
            });
            return row;
        });
    }

    @FXML
    protected void onClickNewRole() {
        processUserSubmission(true);
    }

    @FXML
    protected void onClickUpdateRole() {
        processUserSubmission(false);
    }

    protected void processUserSubmission(boolean isNew) {
        try {
            String roleName = roleField.getText();
            if (isNew) {
                roleService.createRole(roleName);
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Role created.");
                initialize();

            } else {
                Role role = fetchFromInput();
                roleService.updateRole(role);
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Role updated.");
                initialize();
            }
            resetForm();
        } catch (RoleException e) {
            String action = isNew ? "creating" : "updating";
            String role = fetchFromInput().getName();
            String errorMessage = "Error " + action + " Role " + role +" | Reason: " + e.getMessage();
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, errorMessage);
        }
    }

    private Role fetchFromInput() {
        int roleId = Integer.parseInt(roleIdField.getText());
        String name = roleField.getText();

        return new Role(roleId, name);
    }

    protected void resetForm() {
        roleIdField.setText("");
        roleField.setText("");
    }
}
