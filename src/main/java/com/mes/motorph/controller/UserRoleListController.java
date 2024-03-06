package com.mes.motorph.controller;

import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.UserRoleException;
import com.mes.motorph.services.UserRoleService;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Comparator;
import java.util.List;



public class UserRoleListController {

    @FXML
    private Label breadCrumb;
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
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onClickUpdate() {

    }
}