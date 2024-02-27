package com.mes.motorph.controller;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.services.PayrollService;
import com.mes.motorph.utils.AlertUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private TableColumn<Payroll, String> employeeNameColumn;
    @FXML
    private TableColumn<Payroll, Date> fromColumn;
    @FXML
    private TableColumn<Payroll, Date> toColumn;
    @FXML
    private TableColumn<Payroll, Double> daysWorkedColumn;
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
        // Initialize ContextMenu
        setupContextMenu();

        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("payrollId"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
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
        daysWorkedColumn.setCellValueFactory(new PropertyValueFactory<>("daysWorked"));
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

    @FXML
    protected void onClickUpdateBtn()  {
        Payroll selectedPayroll = payrollTableView.getSelectionModel().getSelectedItem();

        if (selectedPayroll != null) {
            String payrollId = selectedPayroll.getPayrollId();
            navigateToPayrollCreateView(payrollId);

        } else {
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to update.");
        }
    }

    // Navigate to update view
    private void navigateToPayrollCreateView(String payrollId) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/payroll-create-view.fxml"));

        try {
            Parent payrollCreateView = loader.load();
            PayrollCreateController payrollCreateController = loader.getController();
            payrollCreateController.setPayrollId(payrollId);

            // Get the main BorderPane from your main view
            BorderPane mainView = (BorderPane) payrollTableView.getScene().getRoot().lookup("#mainView");

            // Replace the center content of the main BorderPane with the payroll-create-view
            mainView.setCenter(payrollCreateView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Right Click ContextMenu
    private void showContextMenu(MouseEvent event, TableRow<Payroll> row, Payroll rowData) {
        ContextMenu contextMenu = new ContextMenu();

        // Update  Action
        MenuItem updateItem = new MenuItem("Update");
        updateItem.setOnAction(e -> {
            String payrollId = rowData.getPayrollId();
            navigateToPayrollCreateView(payrollId);
        });

        // Delete Action
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            String payrollId = rowData.getPayrollId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Payslip #" + payrollId + " ?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    payrollService.deletePayrollById(payrollId);
                    initialize();
                } catch (PayrollException ex) {
                    // Handle exception
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
                }
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
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

    @FXML
    protected void onClickNewPayroll() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mes/motorph/payroll-create-view.fxml"));

        try {
            Parent payrollCreateView = loader.load();
            // Get the main BorderPane from your main view
            BorderPane mainView = (BorderPane) payrollTableView.getScene().getRoot().lookup("#mainView");

            // Replace the center content of the main BorderPane with the payroll-create-view
            mainView.setCenter(payrollCreateView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClickDeletePayroll() throws PayrollException {
        Payroll selectedPayroll = payrollTableView.getSelectionModel().getSelectedItem();

        if (selectedPayroll != null) {
            String payrollId = selectedPayroll.getPayrollId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete Payslip #" + payrollId + " ?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    payrollService.deletePayrollById(payrollId);
                    initialize();
                } catch (PayrollException ex) {
                    // Handle exception
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
                }
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
        } else {
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
        }
    }
}
