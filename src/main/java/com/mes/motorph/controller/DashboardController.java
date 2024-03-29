package com.mes.motorph.controller;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.services.EmployeeService;
import com.mes.motorph.services.PayrollService;
import com.mes.motorph.utils.CurrencyUtility;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.skins.ColorTileSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

public class DashboardController {

    @FXML
    private Text employeeCount;
    @FXML
    private Text payrollExpense;
    @FXML
    private PieChart statusPieChart;
    @FXML
    private Label breadCrumb;
    @FXML
    private BarChart<String, Number> expenseBreakdown;
    @FXML
    private CategoryAxis xAxis; // Assuming this is your CategoryAxis
    @FXML
    private NumberAxis yAxis;   // Assuming this is your NumberAxis


    EmployeeService employeeService = new EmployeeService();
    PayrollService payrollService = new PayrollService();


    @FXML
    public void initialize() throws EmployeeException, PayrollException {
        breadCrumb.setText("Overview / Dashboard");

        List<Employee> employees = employeeService.fetchAllEmployees();
        List<Payroll> payrolls = payrollService.fetchPayrollList();

        double totalPayrollExpense = 0;

        for (Payroll payroll : payrolls) {
            totalPayrollExpense += payroll.getNetPay();
        }

        // Total employee count
        int totalEmployees = employees.size();

        // Convert to Peso
        NumberFormat philippinesFormat = CurrencyUtility.getPhilippinesCurrencyFormatter();
        String payrollExpenseFormatted = philippinesFormat.format(totalPayrollExpense);

        employeeCount.setText(String.valueOf(totalEmployees));
        payrollExpense.setText(String.valueOf(payrollExpenseFormatted));

        // Count the number of employees in each status category
        Map<String, Integer> statusCount = new HashMap<>();
        for (Employee employee : employees) {
            String status = employee.getStatus(); // Assuming getStatus() returns "Regular" or "Probation"
            statusCount.put(status, statusCount.getOrDefault(status, 0) + 1);
        }

        // Create data for the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : statusCount.entrySet()) {
            PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
            // Set custom color for each slice
            if (data.getNode() != null) {
                switch (entry.getKey()) {
                    case "Regular":
                        data.getNode().setStyle("-fx-pie-color: #39CE90;"); // Green color for Regular status
                        break;
                    case "Probation":
                        data.getNode().setStyle("-fx-pie-color: #FFAB00;"); // Red color for Probation status
                        break;
                    // Add more cases for additional status types if needed
                    default:
                        break;
                }
            }
            pieChartData.add(data);
        }
        // Set data to the pie chart
        statusPieChart.setData(pieChartData);
        statusPieChart.setTitle("Employee Status Breakdown");

        Map<String, Double> departmentExpenseMap = new HashMap<>();
        for (Payroll payroll : payrolls) {
            String deptId = payroll.getDepartment();
            if (deptId != null) { // Add a null check here
                double netPay = payroll.getNetPay();
                departmentExpenseMap.put(deptId, departmentExpenseMap.getOrDefault(deptId, 0.0) + netPay);
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<String, Double> entry : departmentExpenseMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        // Set the name of the series (optional)
        series.setName("Expense Breakdown");

        // Set the same color for all bars
        String barColorStyle = "-fx-bar-fill: #03d578;";
        // Set the axes for the bar chart
        expenseBreakdown.setCategoryGap(10); // Set the gap between categories
        expenseBreakdown.setBarGap(2);       // Set the gap between bars
        expenseBreakdown.setLegendVisible(true); // Show legend
        expenseBreakdown.setStyle(barColorStyle);
        expenseBreakdown.getData().add(series);

        // Set labels for X and Y axis (optional)
        xAxis.setLabel("Department");
        yAxis.setLabel("Net Pay");
    }
}
