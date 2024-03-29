package com.mes.motorph.controller;

import com.mes.motorph.entity.User;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.services.PasswordService;
import com.mes.motorph.services.UserService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class UserListController {
    @FXML
    private Label breadCrumb;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private FilteredList<User> filteredUsers;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TextField usernameField;
    @FXML
    private Text sceneTitle;
    @FXML
    private TextField usernameSearchField;
    @FXML
    private PasswordField passwordField;

    private UserService userService = new UserService();

    @FXML
    protected void initialize() throws UserException {
        breadCrumb.setText("Administration / Manage / Users");
        sceneTitle.setText("Manager Users");

        setupContextMenu();
        fetchUsers();
    }



    @FXML
    protected void fetchUsers() throws UserException {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        try {
            List<User> users = userService.fetchAllUsers();

            if(users.isEmpty()) {
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "No user data found.");
            } else {
                ObservableList<User> allUsers = FXCollections.observableArrayList(users);
                filteredUsers = new FilteredList<>(allUsers);
                usersTableView.setItems(filteredUsers);

                usernameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.isEmpty()) {
                        filteredUsers.setPredicate(null);
                    }
                });
            }
        } catch (UserException e) {
            throw new UserException("Error loading table: " + e.getMessage(), e);
        }
    }

    @FXML
    protected void setUserDetails(String username) {
        usernameField.setText(username);
    }

    private void showContextMenu(MouseEvent event, TableRow<User> row, User rowData) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> {
            String username = rowData.getUsername();
            setUserDetails(username);
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            String username = rowData.getUsername();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete User" + username + " ?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    userService.deleteUser(username);
                    initialize();
                } catch (UserException ex) {
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
        usersTableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    User rowData = row.getItem();
                    showContextMenu(event, row, rowData);
                }
            });
            return row;
        });
    }


//    TODO : DELETE
//    @FXML
//    protected void onClickNewUser() throws Exception {
//        processUserSubmission(true);
//    }
//
//    @FXML
//    protected void onClickUpdateUser() throws Exception {
//        processUserSubmission(false);
//    }

//    protected void processUserSubmission(boolean isNew) throws Exception {
//        try {
//            User user = fetchFromInput();
//            if (isNew) {
//                userService.createNewUser(user);
//                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "User created.");
//
//                initialize();
//
//            } else {
//                userService.updateUser(user);
//                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Success", null, "User updated.");
//
//            }
//            resetForm();
//        } catch (UserException e) {
//            String action = isNew ? "creating" : "updating";
//            String username = fetchFromInput().getUsername();
//            String errorMessage = "Error " + action + " User " + username +" | Reason: " + e.getMessage();
//            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, errorMessage);
//        }
//    }

//    private User fetchFromInput() throws Exception {
//        String username = usernameField.getText();
//        String password = PasswordService.encrypt(passwordField.getText());
//
//        return new User(username, password);
//    }
//
//    protected void resetForm() {
//        usernameField.setText("");
//        passwordField.setText("");
//    }
}
