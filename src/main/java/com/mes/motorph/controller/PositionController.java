package com.mes.motorph.controller;

import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.services.PositionService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class PositionController {

    @FXML
    private TableView<Position> positionTableView;
    @FXML
    private TableColumn<Position, Integer> positionIdColumn;
    @FXML
    private TableColumn<Position, String> titleColumn;
    @FXML
    private TextField posField;

    PositionService positionService = new PositionService();

    @FXML
    protected void initialize(){
        positionIdColumn.setCellValueFactory(new PropertyValueFactory<>("positionId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        try{
            List<Position> positionList = positionService.fetchPositions();
            ObservableList<Position> positionObservableList = FXCollections.observableArrayList(positionList);
            positionTableView.setItems(positionObservableList);

        } catch (PositionException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    protected void onClickAdd(){
        if(posField.getText().isEmpty()){
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please put a position");
        }else{
            processInput(true);
        }
    }

    @FXML
    protected void onClickDelete(){
        Position selectedRow = positionTableView.getSelectionModel().getSelectedItem();
        if(selectedRow != null ){
            try {
                int positionId = selectedRow.getPositionId();
                positionService.deletePosition(positionId);
                initialize();
            } catch (PositionException e) {
                throw new RuntimeException(e);
            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Select a row to delete");
        }

    }

    @FXML
    protected void onClickUpdate(){
        if(posField.getText().isEmpty()){
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please put a new position");
        }else{
            processInput(false);
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
                String updPos = position.getTitle();
                Position updPosition = new Position(id, updPos);
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



}
