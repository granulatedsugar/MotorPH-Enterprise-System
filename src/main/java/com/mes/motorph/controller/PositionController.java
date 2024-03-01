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
    private String positionTitle;
    private int positionId;

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
        try {
            String title = posField.getText();
            Position position = new Position(title);
            positionService.createPosition(position);
            initialize();
        } catch (PositionException e) {
            throw new RuntimeException(e);
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
        Position selectedRow = positionTableView.getSelectionModel().getSelectedItem();
        try{
            String newPosition= posField.getText();
            if(newPosition.isEmpty()){
                AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please enter a new position");
            }else{
                this.positionId = selectedRow.getPositionId();
                this.positionTitle = newPosition;
                Position position = new Position(positionId,positionTitle);
                positionService.updatePosition(position);
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Update", null, "Updated Position");
            }
        } catch (PositionException e) {
            throw new RuntimeException(e);
        }


    }



}
