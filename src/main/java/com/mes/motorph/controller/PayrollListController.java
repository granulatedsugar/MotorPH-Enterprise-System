package com.mes.motorph.controller;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.services.PayrollService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PayrollListController {
    @FXML
    private TableView<Payroll> payrollTableView;
    @FXML
    private TableColumn<Payroll, String> idColumn;
    @FXML
    private TableColumn<Payroll, Integer> employeeIdColumn;
    @FXML
    private TableColumn<Payroll, Date> fromColumn;
    @FXML
    private TableColumn<Payroll, Date> toColumn;
    @FXML
    private TableColumn<Payroll, Double> totalHoursColumn;
    @FXML
    private TableColumn<Payroll, Double> totalDeductionColumn;
    @FXML
    private TableColumn<Payroll, Double> totalAllowanceColumn;
    @FXML
    private TableColumn<Payroll, Double> grossPayColumn;
    @FXML
    private TableColumn<Payroll, Double> netPayColumn;


    private PayrollService payrollService = new PayrollService();

    @FXML
    protected void initialize() throws PayrollException {
        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("payrollId"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("payPeriodFrom"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("payPeriodTo"));
        totalHoursColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));
        totalDeductionColumn.setCellValueFactory(new PropertyValueFactory<>("totalDeduction"));
        totalAllowanceColumn.setCellValueFactory(new PropertyValueFactory<>("totalAllowance"));
        grossPayColumn.setCellValueFactory(new PropertyValueFactory<>("grossPay"));
        netPayColumn.setCellValueFactory(new PropertyValueFactory<>("netPay"));

        // Fetch and display worked hours
        try {
            if (!payrollService.hasPayrollData()) {
                // Show pop-up if no payroll data found
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("No payroll data found.");
                alert.showAndWait();
            } else {
                List<Payroll> payrolls = payrollService.fetchPayrollList();
                ObservableList<Payroll> payrollObservableList = FXCollections.observableArrayList(payrolls);
                payrollTableView.setItems(payrollObservableList);
            }
        } catch (SQLException e) {
            throw new PayrollException("Error loading table: " + e.getMessage(), e);
        }
    }

}
