package com.mes.motorph.services;

import com.mes.motorph.entity.Payroll;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.repository.PayrollRepository;

import java.util.List;

public class PayrollService {

    PayrollRepository payrollRepository = new PayrollRepository();

    public List<Payroll> fetchWorkedHours() throws PayrollException {
        return payrollRepository.fetchWorkedHours();
    }
}
