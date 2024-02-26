package com.mes.motorph.entity;

public class Department {
    private int deptId;
    private String deptDesc;

    public Department(int deptId, String deptDesc) {
        this.deptId = deptId;
        this.deptDesc = deptDesc;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }
}
