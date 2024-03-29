package com.mes.motorph.services;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.repository.PayrollRepository;

import java.sql.SQLException;
import java.util.List;

public class PayrollService {

    PayrollRepository payrollRepository = new PayrollRepository();

    public List<Payroll> fetchPayrollList() throws PayrollException {
        return payrollRepository.fetchPayrollList();
    }

    public boolean hasPayrollData() throws PayrollException, SQLException {
        return payrollRepository.hasPayrollData();
    }

    public Payroll fetchEmployeePayrollDetails(String payrollId) throws PayrollException {
        return payrollRepository.fetchEmployeePayrollDetails(payrollId);
    }

    public void createNewPayslip(Payroll payroll) throws PayrollException {
        payrollRepository.createNewPayslip(payroll);
    }

    public void updatePayroll(Payroll payroll) throws PayrollException {
        payrollRepository.updatePayslip(payroll);
    }

    public void deletePayrollById(String payrollId) throws PayrollException {
        payrollRepository.deletePayrollById(payrollId);
    }
}
