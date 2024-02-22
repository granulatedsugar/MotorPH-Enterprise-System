package com.mes.motorph.controller;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.services.PayrollService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class PayrollListController {
    @FXML
    private TableView<Payroll> payrollTableView;
    @FXML
    private FilteredList<Payroll> filteredPayrolls;
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
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtEmployeeId;


    private PayrollService payrollService = new PayrollService();

    @FXML
    protected void initialize() throws PayrollException {
        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("payrollId"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        // For Date columns, use custom cell value factories to format Date objects
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("payPeriodFrom"));
        fromColumn.setCellFactory(column -> new TableCell<Payroll, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString()); // Customize the date formatting as needed
                }
            }
        });
        toColumn.setCellValueFactory(new PropertyValueFactory<>("payPeriodTo"));
        toColumn.setCellFactory(column -> new TableCell<Payroll, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString()); // Customize the date formatting as needed
                }
            }
        });
        totalHoursColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));
        totalDeductionColumn.setCellValueFactory(new PropertyValueFactory<>("totalDeduction"));
        totalAllowanceColumn.setCellValueFactory(new PropertyValueFactory<>("totalAllowance"));
        grossPayColumn.setCellValueFactory(new PropertyValueFactory<>("grossPay"));
        netPayColumn.setCellValueFactory(new PropertyValueFactory<>("netPay"));

        try {
            List<Payroll> payrolls = payrollService.fetchPayrollList();
            if (payrolls.isEmpty()) {
                // Show pop-up if no payroll data found
                AlertUtility.showAlert(Alert.AlertType.INFORMATION,"Information", null, "No payroll data found.");
            } else {
                ObservableList<Payroll> allPayrolls = FXCollections.observableArrayList(payrolls);
                filteredPayrolls = new FilteredList<>(allPayrolls);
                payrollTableView.setItems(filteredPayrolls);

                datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        filteredPayrolls.setPredicate(null); // Remove the filter
                    } else {
                        filteredPayrolls.setPredicate(payroll -> {
                            // Convert LocalDate to a string in the format "yyyy-MM-dd"
                            String selectedDateString = newValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            // Compare selected date string with the formatted date strings in your Payroll entity
                            return selectedDateString.equals(payroll.getPayPeriodFrom().toString())
                                    || selectedDateString.equals(payroll.getPayPeriodTo().toString());
                        });
                    }
                });
            }
        } catch (PayrollException e) {
            throw new PayrollException("Error loading table: " + e.getMessage(), e);
        }
    }

    @FXML
    protected void onClickSearchEmployeeId() {
        String employeeIdText = txtEmployeeId.getText().trim();

        // Check if not empty
        try {
            int employeeId = Integer.parseInt(employeeIdText); // Convert the input to an integer
            // Create a predicate to filter the list based on the employee ID
            Predicate<Payroll> filterPredicate = payroll -> payroll.getEmployeeId() == employeeId;

            // Apply the predicate to the filtered list
            filteredPayrolls.setPredicate(filterPredicate);

            // Check if any records match the employee ID
            if (filteredPayrolls.isEmpty()) {
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, "No records found for the provided Employee ID.");
            }
        } catch (NumberFormatException e) {
            AlertUtility.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a valid Employee ID.");
        }
    }
}
