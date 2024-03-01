package com.mes.motorph.controller;

import com.mes.motorph.entity.Role;
import com.mes.motorph.exception.RoleException;
import com.mes.motorph.services.RoleService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.List;

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
    private FilteredList filteredRoles;


    private RoleService roleService = new RoleService();

    @FXML
    protected void initialize() {
        breadCrumb.setText("Administation / Manage / Role");
        sceneTitle.setText("Manage Roles");
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
        
    }
}
