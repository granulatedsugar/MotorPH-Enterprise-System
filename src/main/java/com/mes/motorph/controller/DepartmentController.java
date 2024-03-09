package com.mes.motorph.controller;


import com.mes.motorph.entity.Department;
import com.mes.motorph.exception.DepartmentException;
import com.mes.motorph.services.DepartmentService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
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

public class DepartmentController {
    @FXML
    private Label breadCrumb;
    @FXML
    private MFXPaginatedTableView<Department> departmentTableView;
    @FXML
    private MFXTextField departmentField;
    private int deptId;
    private String deptDesc;

    DepartmentService departmentService = new DepartmentService();

    @FXML
    protected void initialize() {
        setupTable();
        breadCrumb.setText("Administration / Department / Manage");
        departmentTableView.autosizeColumnsOnInitialization();
        departmentTableView.currentPageProperty().addListener((observable, oldValue, newValue) -> departmentTableView.autosizeColumns());
    }

    private void setupTable() {
        // Clear existing columns
        departmentTableView.getTableColumns().clear();

        MFXTableColumn<Department> descColumn = new MFXTableColumn<>("Department", true, Comparator.comparing(Department::getDeptDesc));
        MFXTableColumn<Department> updateButton = new MFXTableColumn<>("", true, Comparator.comparing(Department::getDeptId));
        MFXTableColumn<Department> deleteButton = new MFXTableColumn<>("", true, Comparator.comparing(Department::getDeptId));


        descColumn.setRowCellFactory(department -> new MFXTableRowCell<>(Department::getDeptDesc));

        updateButton.setRowCellFactory(department -> new MFXTableRowCell<>(departments -> "") {
            {
                updateButton.setAlignment(Pos.CENTER);
                updateButton.setMinWidth(62);
                updateButton.setMaxWidth(62);
                updateButton.setColumnResizable(false);

                deptDesc = department.getDeptDesc();

                MFXButton button = createButton("🖊", "mfx-button-table-update", event -> {
                    Department selectedDepartment = departmentTableView.getSelectionModel().getSelectedValues().get(deptId);
                    setData(selectedDepartment);
                });
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        deleteButton.setRowCellFactory(department -> new MFXTableRowCell<>(departments -> "") {
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

        departmentTableView.getTableColumns().addAll(descColumn, deleteButton, updateButton);

        departmentTableView.getFilters().addAll(
                new StringFilter<>("Department", Department::getDeptDesc)
        );

        try {
            List<Department> deptList = departmentService.fetchDepartments();
            departmentTableView.getItems().clear(); // Clear existing items
            if (!deptList.isEmpty()) {
                departmentTableView.setItems(FXCollections.observableArrayList(deptList));
            } else {
                // Handle case when roleList is empty
                // Display a message to the user or adjust the behavior of your application accordingly
                System.out.println("Department list is empty.");
            }
        } catch (DepartmentException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
        }
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    protected void setData(Department department) {
        departmentField.setFloatingText("Update Department");
        departmentField.setText(department.getDeptDesc());
        deptId = department.getDeptId();
    }

    private void confirmAndDeleteRole() {
        Department selectedDepartment = departmentTableView.getSelectionModel().getSelectedValues().get(deptId);

        if (selectedDepartment != null) {
            int id = selectedDepartment.getDeptId();
            String desc = selectedDepartment.getDeptDesc();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete department " + desc  + " ?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    System.out.println(id);
                    departmentService.deleteDepartment(id);
                    initialize();
                } catch (DepartmentException ex) {
                    // Handle exception
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
                }
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
        }
    }

    @FXML
    private void onClickSubmit() {
        if (deptId != 0) {
            deptDesc = departmentField.getText();
            confirmAndUpdateDepartment();
        } else if (deptId == 0 && !departmentField.getText().isEmpty()) {
            deptDesc = departmentField.getText();
            createNewDepartment();
        } else {
            showAlert("Please select a department to update.");
        }
        initialize();
    }

    private void confirmAndUpdateDepartment() {
        Alert alert = createConfirmationAlert("Are you sure you want to update " + deptDesc + " ?");
        alert.showAndWait();
        try {
            updateDepartmentSelected();
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "Updated department.");
        } catch (DepartmentException e) {
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Unable to update record.");
        }
    }
    private void createNewDepartment() {
        try {
            String newDepartment = departmentField.getText();

            Department department = new Department(newDepartment);
            departmentService.createDepartment(department);
            showAlert("Department Created!");
        } catch (DepartmentException e) {
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

    protected void updateDepartmentSelected() throws DepartmentException {
        deptDesc = departmentField.getText();
        departmentService.updateDepartment(deptDesc, deptId);
    }

    private void handleRoleException(DepartmentException ex) {
        AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Error updating position: " + ex.getMessage());
        ex.printStackTrace();
    }
}