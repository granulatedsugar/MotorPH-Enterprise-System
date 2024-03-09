package com.mes.motorph.controller;

import com.mes.motorph.entity.Role;
import com.mes.motorph.entity.UserRole;
import io.github.palexdev.materialfx.controls.MFXButton;
import com.mes.motorph.exception.RoleException;
import com.mes.motorph.services.RoleService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
    private int id;

    private RoleService roleService = new RoleService();

    @FXML
    protected void initialize() {
        setupTable();
        breadCrumb.setText("Administration / Role / Create");
    }

    private void setupTable() {
        // Clear existing columns
        rolesTableView.getTableColumns().clear();

        MFXTableColumn<Role> idColumn = new MFXTableColumn<>("Role ID", true, Comparator.comparing(Role::getRoleId));
        MFXTableColumn<Role> nameColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(Role::getName));
        MFXTableColumn<Role> updateButton = new MFXTableColumn<>("", true, Comparator.comparing(Role::getRoleId));
        MFXTableColumn<Role> deleteButton = new MFXTableColumn<>("", true, Comparator.comparing(Role::getRoleId));

        idColumn.setRowCellFactory(role -> new MFXTableRowCell<>(Role::getRoleId));
        nameColumn.setRowCellFactory(role -> new MFXTableRowCell<>(Role::getName));
        nameColumn.setMinWidth(250);

        updateButton.setRowCellFactory(role -> new MFXTableRowCell<>(roles -> "") {
            {
                updateButton.setAlignment(Pos.CENTER);
                updateButton.setMinWidth(62);
                updateButton.setMaxWidth(62);
                updateButton.setColumnResizable(false);

                MFXButton button = createButton("🖊", "mfx-button-table-update", event -> { Role selectedRole = rolesTableView.getSelectionModel().getSelectedValues().get(roleId);
                // Pass the selected row to setData() method
                setData(selectedRole); });
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        deleteButton.setRowCellFactory(userRole -> new MFXTableRowCell<>(rowUserRole -> "") {
                {
                    deleteButton.setAlignment(Pos.CENTER);
                    deleteButton.setMinWidth(62);
                    deleteButton.setMaxWidth(62);
                    deleteButton.setColumnResizable(false);

                    MFXButton button = createButton("⛔", "mfx-button-table-delete", event -> confirmAndDeleteRole());
                    setGraphic(button);

                    mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println(newValue);
                        if (newValue) {
                            setMouseTransparent(false);
                        }
                    });
                }
            });

        rolesTableView.getTableColumns().addAll(idColumn, nameColumn, deleteButton, updateButton);

        rolesTableView.getFilters().addAll(
                new IntegerFilter<>("Role ID", Role::getRoleId),
                new StringFilter<>("Description", Role::getName)
        );

        try {
            List<Role> roleList = roleService.fetchAllRoles();
            rolesTableView.getItems().clear(); // Clear existing items
            rolesTableView.setItems(FXCollections.observableArrayList(roleList));
        } catch (RoleException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
        }
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    protected void setData(Role role) {
        roleNameField.setText(role.getName());
        roleId = role.getRoleId();
    }


    private void confirmAndDeleteRole() {
        List<Role> selectedRoles = rolesTableView.getSelectionModel().getSelectedValues();
        if (!selectedRoles.isEmpty()) { // Check if any items are selected
            Role selectedRole = selectedRoles.get(0); // Assuming you want the first selected role

            int roleId = selectedRole.getRoleId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Role #" + roleId + " ?");

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
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Error deleting role.");
                }
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
        } else {
            // Show this message when no row is selected
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
        }
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
        initialize();
    }

    private void handleRoleException(RoleException ex) {
        AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Error updating role: " + ex.getMessage());
        ex.printStackTrace();
    }

}

