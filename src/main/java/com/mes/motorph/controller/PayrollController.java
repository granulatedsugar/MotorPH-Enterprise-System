package com.mes.motorph.controller;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.services.DeductionService;
import com.mes.motorph.services.PayrollService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

public class PayrollController {
    @FXML
    private Label lblSssDeduction;
    @FXML
    private TextField txtSalary;

    @FXML
    private TableView<Payroll> payrollTableView;

    @FXML
    private TableColumn<Payroll, Integer> idColumn;

    @FXML
    private TableColumn<Payroll, String> dateColumn;

    @FXML
    private TableColumn<Payroll, Integer> employeeIdColumn;



    private DeductionService deduction = new DeductionService();
    private PayrollService payrollService = new PayrollService();

    @FXML
    protected void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        // Add other column initializations here

        // Fetch and display worked hours
        try {
            List<Payroll> payrolls = payrollService.fetchWorkedHours();
            ObservableList<Payroll> payrollObservableList = FXCollections.observableArrayList(payrolls);
            payrollTableView.setItems(payrollObservableList);
        } catch (PayrollException e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }


    @FXML
    protected void onClickCalculate() {
        double salary = Double.parseDouble(txtSalary.getText());
        double sssDeduction = deduction.calculateSssDeduction(salary);
        lblSssDeduction.setText(String.valueOf(sssDeduction));
        System.out.print("I have run and clicked! " + sssDeduction);
    }

}
