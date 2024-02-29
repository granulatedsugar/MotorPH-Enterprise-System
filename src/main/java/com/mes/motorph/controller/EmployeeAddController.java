package com.mes.motorph.controller;

import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.services.DepartmentService;
import com.mes.motorph.services.PositionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.List;

public class EmployeeAddController {

    @FXML
    private TextField firstNameAdd;
    @FXML
    private TextField lastNameAdd;
    @FXML
    private TextField addressAdd;
    @FXML
    private TextField emailAdd;
    @FXML
    private TextField phoneNumAdd;
    @FXML
    private TextField clothingAllowanceAdd;
    @FXML
    private TextField phoneAllowanceAdd;
    @FXML
    private TextField riceSubAdd;
    @FXML
    private TextField pagIbigAdd;
    @FXML
    private TextField philHealthAdd;
    @FXML
    private TextField sssAdd;
    @FXML
    private TextField tinAdd;
    @FXML
    private TextField supervisorAdd;
    @FXML
    private TextField statusAdd;
    @FXML
    private TextField basicSalaryAdd;
    @FXML
    private TextField grossSemiMonthlyRateAdd;
    @FXML
    private TextField hourlyRateAdd;
    @FXML
    private TextField vacationHoursAdd;
    @FXML
    private TextField sickHoursAdd;
    @FXML
    private DatePicker dobAdd;
    @FXML
    private ComboBox<Position> positionAdd;
    @FXML ComboBox<String> departmentAdd;

    //Services
    PositionService positionService = new PositionService();
    DepartmentService departmentService = new DepartmentService();


    @FXML
    protected void initialize() {

        try {
            List<Position> positions = positionService.fetchPositions();
            ObservableList<Position> positionObservableList = FXCollections.observableArrayList(positions);
            positionAdd.setItems(positionObservableList);

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }
}
