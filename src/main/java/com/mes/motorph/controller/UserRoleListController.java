package com.mes.motorph.controller;

import com.mes.motorph.Main;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.UserRoleException;
import com.mes.motorph.services.UserRoleService;
import com.mes.motorph.utils.AlertUtility;
import com.mes.motorph.view.ViewFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class UserRoleListController {

    @FXML
    private Label breadCrumb;
    @FXML
    private MFXPaginatedTableView<UserRole> userRolesTableView;
    private int id;
    UserRoleService userRoleService = new UserRoleService();

//    Image updateImage = new Image(getClass().getResourceAsStream("update.png"));

    public void initialize() {
        setupTable();
        breadCrumb.setText("Administration / User / Roles");
        userRolesTableView.autosizeColumnsOnInitialization();
        userRolesTableView.currentPageProperty().addListener((observable, oldValue, newValue) -> userRolesTableView.autosizeColumns());
    }

    private void setupTable() {
        // Clear existing columns
        userRolesTableView.getTableColumns().clear();

        MFXTableColumn<UserRole> idColumn = new MFXTableColumn<>("Employee ID", true, Comparator.comparing(UserRole::getEmpID));
        MFXTableColumn<UserRole> firstNameColumn = new MFXTableColumn<>("First Name", true, Comparator.comparing(UserRole::getFirstName));
        MFXTableColumn<UserRole> lastNameColumn = new MFXTableColumn<>("Last Name", true, Comparator.comparing(UserRole::getLastName));
        MFXTableColumn<UserRole> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(UserRole::getEmail));
        MFXTableColumn<UserRole> positionColumn = new MFXTableColumn<>("Job Title", true, Comparator.comparing(UserRole::getPosition));
        MFXTableColumn<UserRole> departmentColumn = new MFXTableColumn<>("Department", true, Comparator.comparing(UserRole::getDepartment));
        MFXTableColumn<UserRole> roleColumn = new MFXTableColumn<>("Role", true, Comparator.comparing(UserRole::getRoles));
//        MFXTableColumn<UserRole> updateButton = new MFXTableColumn<>("", true, Comparator.comparing(UserRole::getEmpID));
        MFXTableColumn<UserRole> deleteButton = new MFXTableColumn<>("", true, Comparator.comparing(UserRole::getEmpID));

        idColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getEmpID));
        firstNameColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getFirstName));
        lastNameColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getLastName));
        emailColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getEmail));
        positionColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getPosition));
        departmentColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getDepartment));
        roleColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getRoles));

//        updateButton.setRowCellFactory(userRole -> new MFXTableRowCell<>(rowUserRole -> "") {
//            {
//                updateButton.setAlignment(Pos.CENTER);
//                updateButton.setMinWidth(62);
//                updateButton.setMaxWidth(62);
//                updateButton.setColumnResizable(false);
//
//                MFXButton button = createButton("🖊", "mfx-button-table-update", event -> System.out.println("PRESSED!!!"));
//                setGraphic(button);
//
//                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
//                    System.out.println(newValue);
//                    if (newValue) {
//                        setMouseTransparent(false);
//                    }
//                });
//            }
//        });

        deleteButton.setRowCellFactory(userRole -> new MFXTableRowCell<>(rowUserRole -> "") {
            {
                deleteButton.setAlignment(Pos.CENTER);
                deleteButton.setMinWidth(62);
                deleteButton.setMaxWidth(62);
                deleteButton.setColumnResizable(false);

                MFXButton button = createButton("⛔", "mfx-button-table-delete", event -> deleteSelected());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        userRolesTableView.getTableColumns().addAll(idColumn, firstNameColumn, lastNameColumn, emailColumn, positionColumn, departmentColumn, roleColumn, deleteButton);

        userRolesTableView.getFilters().addAll(
                new IntegerFilter<>("Employee ID", UserRole::getEmpID),
                new StringFilter<>("Email", UserRole::getEmail),
                new StringFilter<>("Role", UserRole::getRoles)
        );

        try {
            List<UserRole> userRoleList = userRoleService.fetchAllUserRolesView();
            userRolesTableView.getItems().clear(); // Clear existing items
            userRolesTableView.setItems(FXCollections.observableArrayList(userRoleList));
        } catch (UserRoleException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void deleteSelected() {
        UserRole selectedUser = userRolesTableView.getSelectionModel().getSelectedValues().get(id);
        if (selectedUser != null) { // Check if the list is not empty
            int logId = selectedUser.getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to remove user role?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    userRoleService.deleteUserRole(logId);
                    initialize();
                } catch (UserRoleException ex) {
                    // Handle exception
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Error deleting user role.");
                }
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
        } else {
            // Show this message when no row is selected
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
        }
    }



    // Method to create and configure a button
    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    @FXML
    protected void onClickCreateRole() {
        navigateToCreateAssigneRole("/com/mes/motorph/role-list-view.fxml", "Manage Roles");
    }

    @FXML
    protected void onClickAssignRole() {
        navigateToCreateAssigneRole("/com/mes/motorph/user-assign-list-view.fxml", "Assign Role");
    }

    private void navigateToCreateAssigneRole(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), 768, 650);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
            // Load the application icon
            Image icon = new Image(Main.class.getResourceAsStream("/images/app-icon.png"));
            stage.getIcons().add(icon);

        } catch (Exception e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}