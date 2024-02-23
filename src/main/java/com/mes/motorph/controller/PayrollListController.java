package com.mes.motorph.controller;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.services.EmployeeService;
import com.mes.motorph.services.PayrollService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
    private EmployeeService employeeService = new EmployeeService();

    @FXML
    protected void initialize() throws PayrollException {
        // Initialize ContextMenu
        setupContextMenu();

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

                // Add listener to txtEmployeeId to reset table when text is cleared
                txtEmployeeId.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.isEmpty()) {
                        filteredPayrolls.setPredicate(null); // Reset the predicate to show all records
                    }
                });

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
    protected void onClickSearchEmployeeId() throws EmployeeException {
        String employeeIdText = txtEmployeeId.getText().trim();

        // Check if not empty
        try {
            int employeeId = Integer.parseInt(employeeIdText); // Convert the input to an integer
            // Create a predicate to filter the list based on the employee ID
            Predicate<Payroll> filterPredicate = payroll -> payroll.getEmployeeId() == employeeId;

            // TODO: Delete! After Test!
            System.out.println(employeeService.fetchEmployeeDetails(employeeId).toString());
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

    // Right Click ContextMenu
    private void showContextMenu(MouseEvent event, TableRow<Payroll> row, Payroll rowData) {
        ContextMenu contextMenu = new ContextMenu();

        // Update  Action
        MenuItem updateItem = new MenuItem("Update");
        updateItem.setOnAction(e -> {
            // TODO: Remove toString after testing
            System.out.println("Update mee!" + rowData.toString());
        });

        // Delete Acttion
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            // TODO: Remove toString after testing
            System.out.println("Delete mee!" + rowData.toString());
        });

        contextMenu.getItems().addAll(updateItem, deleteItem);

        // Show the context menu at the mouse cursor's location
        contextMenu.show(row, event.getScreenX(), event.getScreenY());
    }

    // Right Click ContextMenu
    private void setupContextMenu() {
        payrollTableView.setRowFactory(tv -> {
            TableRow<Payroll> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    Payroll rowData = row.getItem();
                    showContextMenu(event, row, rowData);
                }
            });
            return  row;
        });
    }
}
