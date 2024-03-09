package com.mes.motorph.controller;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.services.PayrollService;
import com.mes.motorph.utils.AlertUtility;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PayrollListController {
    @FXML
    private MFXPaginatedTableView<Payroll> payrollTableView;
    @FXML
    private Label breadCrumb;
    private int id;



    private PayrollService payrollService = new PayrollService();

    @FXML
    protected void initialize() {
        setupTable();
        breadCrumb.setText("Payroll / Statements");
        payrollTableView.autosizeColumnsOnInitialization();
        payrollTableView.currentPageProperty().addListener((observable, oldValue, newValue) -> payrollTableView.autosizeColumns());
    }

    private void setupTable() {
        payrollTableView.getTableColumns().clear();

        MFXTableColumn<Payroll> payslipIdColumn = new MFXTableColumn<>("Payslip No.", true, Comparator.comparing(Payroll::getPayrollId));
        MFXTableColumn<Payroll> empIdColumn = new MFXTableColumn<>("Employee ID", true, Comparator.comparing(Payroll::getEmployeeId));
        MFXTableColumn<Payroll> empNameColumn = new MFXTableColumn<>("Employee Name", true, Comparator.comparing(Payroll::getEmployeeName));
        MFXTableColumn<Payroll> fromColumn = new MFXTableColumn<>("Start Date", true, Comparator.comparing(Payroll::getPayPeriodFrom));
        MFXTableColumn<Payroll> toColumn = new MFXTableColumn<>("End Date", true, Comparator.comparing(Payroll::getPayPeriodTo));
        MFXTableColumn<Payroll> daysWorkedColumn = new MFXTableColumn<>("Days Worked", true, Comparator.comparing(Payroll::getDaysWorked));
        MFXTableColumn<Payroll> deductionColumn = new MFXTableColumn<>("Total Deduction", true, Comparator.comparing(Payroll::getTotalDeduction));
        MFXTableColumn<Payroll> allowanceColumn = new MFXTableColumn<>("Total Deduction", true, Comparator.comparing(Payroll::getTotalAllowance));
        MFXTableColumn<Payroll> grossColumn = new MFXTableColumn<>("Total Deduction", true, Comparator.comparing(Payroll::getGrossPay));
        MFXTableColumn<Payroll> netColumn = new MFXTableColumn<>("Total Deduction", true, Comparator.comparing(Payroll::getNetPay));
        MFXTableColumn<Payroll> deleteButton = new MFXTableColumn<>("", true, Comparator.comparing(Payroll::getEmployeeId));
        MFXTableColumn<Payroll> updateButton = new MFXTableColumn<>("", true, Comparator.comparing(Payroll::getEmployeeId));

        payslipIdColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getPayrollId));
        empIdColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getEmployeeId));
        empNameColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getEmployeeName));
        fromColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getPayPeriodFrom));
        toColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getPayPeriodTo));
        daysWorkedColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getDaysWorked));
        deductionColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getTotalDeduction));
        allowanceColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getTotalAllowance));
        grossColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getGrossPay));
        netColumn.setRowCellFactory(payroll -> new MFXTableRowCell<>(Payroll::getNetPay));

        deleteButton.setRowCellFactory(payroll -> new MFXTableRowCell<>(payrolls -> "") {
            {
                deleteButton.setAlignment(Pos.CENTER);
                deleteButton.setMinWidth(62);
                deleteButton.setMaxWidth(62);
                deleteButton.setColumnResizable(false);

                MFXButton button = createButton("⛔", "mfx-button-table-delete", event -> onClickDeletePayroll());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        updateButton.setRowCellFactory(payroll -> new MFXTableRowCell<>(payrolls -> "") {
            {
                updateButton.setAlignment(Pos.CENTER);
                updateButton.setMinWidth(62);
                updateButton.setMaxWidth(62);
                updateButton.setColumnResizable(false);

                id = payroll.getEmployeeId();

                MFXButton button = createButton("🖊", "mfx-button-table-update", event -> onClickUpdateBtn());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        payrollTableView.getTableColumns().addAll(payslipIdColumn, empNameColumn, fromColumn, toColumn, deductionColumn, allowanceColumn, grossColumn, netColumn, deleteButton, updateButton);

        payrollTableView.getFilters().addAll(
                new StringFilter<>("Payslip No.", Payroll::getPayrollId),
                new IntegerFilter<>("Employee ID", Payroll::getEmployeeId),
                new StringFilter<>("Employee Name", Payroll::getEmployeeName)
        );

        try {
            List<Payroll> payrollList = payrollService.fetchPayrollList();
            payrollTableView.getItems().clear();
            payrollTableView.setItems(FXCollections.observableArrayList(payrollList));
        } catch (PayrollException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
        }
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    protected void onClickUpdateBtn()  {
        Payroll selectedPayroll = payrollTableView.getSelectionModel().getSelection().get(id);

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

    protected void onClickDeletePayroll() {
        Payroll selectedPayroll = payrollTableView.getSelectionModel().getSelection().get(id);

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
