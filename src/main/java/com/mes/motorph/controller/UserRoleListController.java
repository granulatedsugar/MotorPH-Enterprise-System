package com.mes.motorph.controller;

import com.mes.motorph.entity.User;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.UserRoleException;
import com.mes.motorph.services.UserRoleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class UserRoleListController {
    @FXML
    private TableView<UserRole> userRolesTableView;
    @FXML
    private TableColumn<UserRole, Integer> userRolesIdColumn;
    @FXML
    private TableColumn<UserRole, String> userRoleEmailColumn;
    @FXML
    private TableColumn<UserRole, String >usersRoleColumn;

    UserRoleService userRoleService = new UserRoleService();

    @FXML
    protected void initialize(){
        userRolesIdColumn.setCellValueFactory(new PropertyValueFactory<>("Employee ID"));
        userRoleEmailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        usersRoleColumn.setCellValueFactory(new PropertyValueFactory<>("Roles"));

        try {
            List<UserRole> userRoleList = userRoleService.fetchAllUserRolesView();
            ObservableList<UserRole> userRoleObservableList = FXCollections.observableArrayList(userRoleList);
            userRolesTableView.setItems(userRoleObservableList);
        } catch (UserRoleException e) {
            throw new RuntimeException(e);
        }

    }





}
