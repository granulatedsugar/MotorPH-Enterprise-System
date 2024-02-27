package com.mes.motorph.services;

import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    EmployeeRepository employeeRepository = new EmployeeRepository();

    public Employee fetchEmployeeDetails(int employeeId) throws EmployeeException {
        return employeeRepository.fetchEmployeeDetails(employeeId);
    }
    public List<Employee> fetchAllEmployees() throws EmployeeException {
        return employeeRepository.fetchAllEmployees();
    }
}
