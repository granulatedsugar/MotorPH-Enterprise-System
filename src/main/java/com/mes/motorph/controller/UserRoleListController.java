package com.mes.motorph.controller;

import com.mes.motorph.Main;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.UserRoleException;
import com.mes.motorph.services.UserRoleService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXContextMenuItem;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;



public class UserRoleListController {

    @FXML
    private Label breadCrumb;
    @FXML
    private ContextMenu usrRoleContextMenu;
    @FXML
    private MenuItem usrRoleContextMenuItemUpdate;
    @FXML
    private MFXContextMenuItem updateItem;
    UserRoleService userRoleService = new UserRoleService();

    @FXML
    private MFXPaginatedTableView<UserRole> userRolesTableView;

    public void initialize() {
        setupTable();
        breadCrumb.setText("Administration / User / Roles");
        userRolesTableView.autosizeColumnsOnInitialization();
        userRolesTableView.currentPageProperty().addListener((observable, oldValue, newValue) -> userRolesTableView.autosizeColumns());
    }

    private void setupTable() {
        MFXTableColumn<UserRole> idColumn = new MFXTableColumn<>("Employee ID", true, Comparator.comparing(UserRole::getEmpID));
        MFXTableColumn<UserRole> firstNameColumn = new MFXTableColumn<>("First Name", true, Comparator.comparing(UserRole::getFirstName));
        MFXTableColumn<UserRole> lastNameColumn = new MFXTableColumn<>("Last Name", true, Comparator.comparing(UserRole::getLastName));
        MFXTableColumn<UserRole> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(UserRole::getEmail));
        MFXTableColumn<UserRole> positionColumn = new MFXTableColumn<>("Job Title", true, Comparator.comparing(UserRole::getPosition));
        MFXTableColumn<UserRole> departmentColumn = new MFXTableColumn<>("Department", true, Comparator.comparing(UserRole::getDepartment));
        MFXTableColumn<UserRole> roleColumn = new MFXTableColumn<>("Role", true, Comparator.comparing(UserRole::getRoles));

        idColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getEmpID));
        firstNameColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getFirstName));
        lastNameColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getLastName));
        emailColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getEmail));
        positionColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getPosition));
        departmentColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getDepartment));
        roleColumn.setRowCellFactory(userRole -> new MFXTableRowCell<>(UserRole::getRoles));

        userRolesTableView.getTableColumns().addAll(idColumn, firstNameColumn, lastNameColumn, emailColumn, positionColumn, departmentColumn, roleColumn);
        userRolesTableView.getFilters().addAll(
                new IntegerFilter<>("Employee ID", UserRole::getEmpID),
                new StringFilter<>("Email", UserRole::getEmail),
                new StringFilter<>("Role", UserRole::getRoles)
        );

        try {
            List<UserRole> userRoleList = userRoleService.fetchAllUserRolesView();
            userRolesTableView.setItems(FXCollections.observableArrayList(userRoleList));
        } catch (UserRoleException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onClickUpdate() {

    }

    @FXML
    protected void onClickCreateRole() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mes/motorph/role-list-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Role");
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

    @FXML
    protected void onClickAssignRole() {

    }
}