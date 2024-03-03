package com.mes.motorph.controller;

import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.services.PositionService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class PositionController {

    @FXML
    private TableView<Position> positionTableView;
    @FXML
    private TableColumn<Position, Integer> positionIdColumn;
    @FXML
    private TableColumn<Position, String> titleColumn;
    @FXML
    private FilteredList<Position> filteredPosition;
    @FXML
    private TextField posField;
    @FXML
    private TextField positionIdField;

    PositionService positionService = new PositionService();

    @FXML
    protected void initialize(){

        setupContextMenu();
        positionIdColumn.setCellValueFactory(new PropertyValueFactory<>("positionId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        try{
            List<Position> positionList = positionService.fetchPositions();
            if(positionList.isEmpty()){
                AlertUtility.showAlert(Alert.AlertType.INFORMATION,"Information", null, "No position data found.");
            }else{
                ObservableList<Position> allPosition = FXCollections.observableArrayList(positionList);
                filteredPosition = new FilteredList<>(allPosition);
                positionTableView.setItems(filteredPosition);

                positionIdField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    if(newValue.isEmpty()){
                        filteredPosition.setPredicate(null);
                    }
                });
            }

        } catch (PositionException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void onClickSearchPos(){
        String positionIdText = positionIdField.getText().trim();

        try{
            int positionId = Integer.parseInt(positionIdText);

            Predicate<Position> filterPredicate = position -> position.getPositionId() == positionId;

            filteredPosition.setPredicate(filterPredicate);

            if(filteredPosition.isEmpty()){
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "No records found for the provided Position ID.");
            }
        }catch (NumberFormatException e) {
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid position Id");
        }

    }

    @FXML
    private void onClickAdd(){
        if(posField.getText().isEmpty()){
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please put a position");
        }else{
            processInput(true);
        }
    }

    @FXML
    private void onClickDelete() throws PositionException{
        Position selectedRow = positionTableView.getSelectionModel().getSelectedItem();
        if(selectedRow != null){
            int posId = selectedRow.getPositionId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to delete this row?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton,cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == okButton){
                try{
                    positionService.deletePosition(posId);
                    initialize();
                    AlertUtility.showAlert(Alert.AlertType.INFORMATION, "", null, "Row Deleted");
                }catch (PositionException e){
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
                }
            }else{

            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "please select a row to delete");
        }

    }

    private boolean isEmpty(){
        if (posField.getText().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @FXML
    private void onClickUpdate(){
        Position selectedIndex = positionTableView.getSelectionModel().getSelectedItem();
        if(isEmpty()){
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "Please Fill the Field");
        }else{
            if(selectedIndex != null){
                processInput(false);
            }else{
                AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning!", null, "Please Select a row to update");
            }

        }


    }

    protected void processInput(boolean isNew){
        try{
            Position position = fetchPosition();

            if(isNew){
                positionService.createPosition(position);
                initialize();
                resetTextField();
            }else{
                int id = positionTableView.getSelectionModel().getSelectedItem().getPositionId();
                Position updPosition = new Position(id, position.getTitle());
                positionService.updatePosition(updPosition);
                initialize();
                resetTextField();

            }
        } catch (PositionException e) {
            String action = isNew ? "creating" : "updating";
            String position = fetchPosition().getTitle();
            String errorMessage = "error " + action + " the" + position + "| Reason: " + e.getMessage();
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, errorMessage);
        }
    }

    private Position fetchPosition(){
        String positionTitle = posField.getText();
        return new Position(positionTitle);
    }
    private void resetTextField(){
        posField.setText("");
    }


    private void showContextMenu(MouseEvent event, TableRow<Position> row, Position rowData){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem updateMenu = new MenuItem("Update");
        updateMenu.setOnAction(e -> {
            onClickUpdate();
        });

        MenuItem deleteMenu = new MenuItem("Delete");
        deleteMenu.setOnAction(e ->{
            try {
                onClickDelete();
            } catch (PositionException ex) {
                throw new RuntimeException(ex);
            }
        } );


        contextMenu.getItems().addAll(updateMenu, deleteMenu);
        contextMenu.show(row, event.getScreenX(), event.getScreenY());

    }

    private void setupContextMenu() {
        positionTableView.setRowFactory(tableView -> {
            TableRow<Position> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    Position rowData = row.getItem();
                    showContextMenu(event, row, rowData);
                }
            });
            return  row;
        });
    }



}
