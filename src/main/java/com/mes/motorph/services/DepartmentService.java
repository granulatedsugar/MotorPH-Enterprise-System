package com.mes.motorph.services;

import com.mes.motorph.entity.Department;
import com.mes.motorph.exception.DepartmentException;
import com.mes.motorph.repository.DepartmentRepositiory;

import java.util.List;

public class DepartmentService {

    DepartmentRepositiory departmentRepositiory = new DepartmentRepositiory();

    public List<Department> fetchDepartments() throws DepartmentException {
        return departmentRepositiory.fetchDepartments();
    }
}
