package com.mes.motorph.services;

import com.mes.motorph.entity.Department;
import com.mes.motorph.exception.DepartmentException;
import com.mes.motorph.repository.DepartmentRepositiory;

import java.sql.SQLException;
import java.util.List;

public class DepartmentService {

    DepartmentRepositiory departmentRepositiory = new DepartmentRepositiory();

    public List<Department> fetchDepartments() throws DepartmentException {
        return departmentRepositiory.fetchDepartments();
    }

    public void createDepartment(Department department) throws DepartmentException{
        departmentRepositiory.createDepartment(department);
    }

    public void updateDepartment(String deptDesc, int deptId) throws DepartmentException{
        departmentRepositiory.updateDepartment(deptDesc, deptId);
    }

    public void deleteDepartment(int deptId) throws DepartmentException{
        departmentRepositiory.deleteDepartment(deptId);
    }

    public Department fetchDepartmentDescription(int deptId) throws DepartmentException{
        return departmentRepositiory.fetchDepartmentById(deptId);
    }
}
