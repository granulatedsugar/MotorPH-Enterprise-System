package com.mes.motorph.controller;

import com.mes.motorph.entity.User;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.services.UserService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

public class UserListController {
    @FXML
    private Label breadCrumb;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private FilteredList<User> filteredUsers;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TextField usernameField;
    @FXML
    private Text sceneTitle;

    private UserService userService = new UserService();

    @FXML
    protected void initialize() throws UserException {
        breadCrumb.setText("Administration / Manage / Users");
        sceneTitle.setText("Manager Users");

        fetchList();
    }

    @FXML
    protected void fetchList() throws UserException {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        try {
            List<User> users = userService.fetchAllUsers();

            if(users.isEmpty()) {
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "No user data found.");
            } else {
                ObservableList<User> allUsers = FXCollections.observableArrayList(users);
                filteredUsers = new FilteredList<>(allUsers);
                usersTableView.setItems(filteredUsers);

                usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.isEmpty()) {
                        filteredUsers.setPredicate(null);
                    }
                });
            }
        } catch (UserException e) {
            throw new UserException("Error loading table: " + e.getMessage(), e);
        }
    }
}
