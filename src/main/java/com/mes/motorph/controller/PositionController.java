package com.mes.motorph.controller;

import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.services.PositionService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PositionController {
    @FXML
    private Label breadCrumb;
    @FXML
    private MFXPaginatedTableView<Position> positionTableView;
    @FXML
    private MFXTextField positionNameField;
    private String title;
    private int id;

    PositionService positionService = new PositionService();

    @FXML
    protected void initialize() {
        setupTable();
        breadCrumb.setText("Administration / Position / Manage");
        positionTableView.autosizeColumnsOnInitialization();
        positionTableView.currentPageProperty().addListener((observable, oldValue, newValue) -> positionTableView.autosizeColumns());
    }

    private void setupTable() {
        // Clear existing columns
        positionTableView.getTableColumns().clear();

        MFXTableColumn<Position> titleColumn = new MFXTableColumn<>("Position", true, Comparator.comparing(Position::getTitle));
        MFXTableColumn<Position> updateButton = new MFXTableColumn<>("", true, Comparator.comparing(Position::getPositionId));
        MFXTableColumn<Position> deleteButton = new MFXTableColumn<>("", true, Comparator.comparing(Position::getPositionId));

        titleColumn.setRowCellFactory(position -> new MFXTableRowCell<>(Position::getTitle));

        updateButton.setRowCellFactory(position -> new MFXTableRowCell<>(positions -> "") {
            {
                updateButton.setAlignment(Pos.CENTER);
                updateButton.setMinWidth(62);
                updateButton.setMaxWidth(62);
                updateButton.setColumnResizable(false);

                title = position.getTitle();

                MFXButton button = createButton("🖊", "mfx-button-table-update", event -> {
                    Position selectedPosition = positionTableView.getSelectionModel().getSelectedValues().get(id);
                    setData(selectedPosition);
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

        deleteButton.setRowCellFactory(position -> new MFXTableRowCell<>(positions -> "") {
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

        positionTableView.getTableColumns().addAll(titleColumn, deleteButton, updateButton);

        positionTableView.getFilters().addAll(
                new StringFilter<>("Position", Position::getTitle)
        );

        try {
            List<Position> roleList = positionService.fetchPositions();
            positionTableView.getItems().clear(); // Clear existing items
            if (!roleList.isEmpty()) {
                positionTableView.setItems(FXCollections.observableArrayList(roleList));
            } else {
                // Handle case when roleList is empty
                // Display a message to the user or adjust the behavior of your application accordingly
                System.out.println("Role list is empty.");
            }
        } catch (PositionException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
        }
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    protected void setData(Position position) {
        positionNameField.setFloatingText("Update Position");
        positionNameField.setText(position.getTitle());
        id = position.getPositionId();
    }

    private void confirmAndDeleteRole() {
        Position selectedPosition = positionTableView.getSelectionModel().getSelectedValues().get(id);

        if (selectedPosition != null) {
            int positionId = selectedPosition.getPositionId();
            String title = selectedPosition.getTitle();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Position #" + title  + " ?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    System.out.println(positionId);
                   positionService.deletePosition(positionId);
                    initialize();
                } catch (PositionException ex) {
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
        if (id != 0) {
            title = positionNameField.getText();
            confirmAndUpdatePosition();
        } else if (id == 0 && !positionNameField.getText().isEmpty()) {
            title = positionNameField.getText();
            createNewPosition();

        } else {
            showAlert("Please select a role to update.");
        }
        initialize();
    }

    private void confirmAndUpdatePosition() {
        Alert alert = createConfirmationAlert("Are you sure you want to update " + title + " ?");
        alert.showAndWait();
        try {
            updatePositionSelected();
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "Updated role.");
        } catch (PositionException e) {
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Unable to update record.");
        }
    }
    private void createNewPosition() {
        try {
            String newPosition = positionNameField.getText();

            Position position = new Position(newPosition);
            positionService.createPosition(position);
            showAlert("Position Created!");
        } catch (PositionException e) {
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

    protected void updatePositionSelected() throws PositionException {
        title = positionNameField.getText();
        Position position = new Position(id, title);
        positionService.updatePosition(position);
    }

    private void handleRoleException(PositionException ex) {
        AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Error updating position: " + ex.getMessage());
        ex.printStackTrace();
    }
}