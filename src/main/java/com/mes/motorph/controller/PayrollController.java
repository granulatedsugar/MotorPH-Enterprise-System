package com.mes.motorph.controller;

import com.mes.motorph.services.DeductionService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PayrollController {
    @FXML
    private Label lblSssDeduction;
    @FXML
    private TextField txtSalary;


    DeductionService deduction = new DeductionService();


    @FXML
    protected void onClickCalculate() {
        double salary = Double.parseDouble(txtSalary.getText());
        double sssDeduction = deduction.calculateSssDeduction(salary);
        lblSssDeduction.setText(String.valueOf(sssDeduction));
        System.out.print("I have run and clicked! " + sssDeduction);
    }

}
