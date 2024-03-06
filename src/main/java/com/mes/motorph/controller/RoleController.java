package com.mes.motorph.controller;

import com.mes.motorph.entity.Role;
import com.mes.motorph.exception.RoleException;
import com.mes.motorph.services.RoleService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RoleController {
    @FXML
    private Label breadCrumb;
    @FXML
    private MFXPaginatedTableView<Role> rolesTableView;
    @FXML
    private MFXTextField roleNameField;
    @FXML
    private MFXButton submitBtn;
    private String roleName;
    private int roleId;

    private RoleService roleService = new RoleService();

    @FXML
    protected void initialize() {
        setupTable();
        breadCrumb.setText("Administration / Manage / Role");
        setupContextMenu();
    }

    private void setupTable() {
        MFXTableColumn<Role> idColumn = new MFXTableColumn<>("Role ID", true, Comparator.comparing(Role::getRoleId));
        MFXTableColumn<Role> nameColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(Role::getName));

        idColumn.setRowCellFactory(role -> new MFXTableRowCell<>(Role::getRoleId));
        nameColumn.setRowCellFactory(role -> new MFXTableRowCell<>(Role::getName));
        nameColumn.setMinWidth(250);

        rolesTableView.getTableColumns().addAll(idColumn, nameColumn);

        rolesTableView.getFilters().addAll(
                new IntegerFilter<>("Role ID", Role::getRoleId),
                new StringFilter<>("Description", Role::getName)
        );

        try {
            List<Role> roleList = roleService.fetchAllRoles();
            rolesTableView.setItems(FXCollections.observableArrayList(roleList));
        } catch (RoleException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
        }
    }

    private void showContextMenu(MouseEvent event, MFXTableRow<Role> row, Role rowData) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem updateItem = createUpdateMenuItem(rowData);
        MenuItem deleteItem = createDeleteMenuItem(rowData);

        contextMenu.getItems().addAll(updateItem, deleteItem);
        contextMenu.show(row, event.getScreenX(), event.getScreenY());
    }

    private MenuItem createUpdateMenuItem(Role rowData) {
        MenuItem updateItem = new MenuItem("Update");
        updateItem.setOnAction(e -> {
            roleId = rowData.getRoleId();
            roleNameField.setFloatingText("Update Role");
            roleNameField.setText(rowData.getName());
            submitBtn.setText("Update");
        });
        return updateItem;
    }

    private MenuItem createDeleteMenuItem(Role rowData) {
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            roleId = rowData.getRoleId();
            confirmAndDeleteRole(rowData);
        });
        return deleteItem;
    }

    private void confirmAndDeleteRole(Role rowData) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete Role #" + roleName + " ?");

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
    }

    private void setupContextMenu() {
        rolesTableView.setTableRowFactory(tv -> {
            MFXTableRow<Role> row = new MFXTableRow<>(rolesTableView, new Role(roleId, roleName));
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && row != null) {
                    Role rowData = row.getData();
                    showContextMenu(event, row, rowData);
                }
            });
            return row;
        });
    }


    @FXML
    private void onClickSubmit() {
        if (roleId != 0) {
            roleName = roleNameField.getText();
            confirmAndUpdateRole();
        } else if (roleId == 0 && !roleNameField.getText().isEmpty()) {
            roleName = roleNameField.getText();
            createNewRole();
        } else {
            showAlert("Please select a role to update.");
        }
    }

    private void confirmAndUpdateRole() {
        Alert alert = createConfirmationAlert("Are you sure you want to update " + roleName + " ?");
        alert.showAndWait();
        try {
            updateRoleSelected();
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "Updated role.");
        } catch (RoleException e) {
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Unable to update record.");
        }
    }

    private void createNewRole() {
        try {
            roleService.createRole(roleNameField.getText());
            showAlert("Role Created!");
        } catch (RoleException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(String message) {
        AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, message);
    }

    private Alert createConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE), new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE));
        return alert;
    }

    protected void updateRoleSelected() throws RoleException {
        roleName = roleNameField.getText();
        roleService.updateRole(roleId, roleName);
    }

    private void handleRoleException(RoleException ex) {
        AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Error updating role: " + ex.getMessage());
        ex.printStackTrace();
    }

}

