package com.mes.motorph.entity;

import java.sql.Date;

public class LeaveRequest {
    private int leaveReqId;
    private int employeeId;
    private Date regDate;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date approveDate;


    private String name;
    private String supervisor;
    private int deptId;
    private String deptDesc;


    public LeaveRequest(int leaveReqId, int employeeId, Date regDate, String leaveType, Date startDate, Date endDate, String status, Date approveDate) {
        this.leaveReqId = leaveReqId;
        this.employeeId = employeeId;
        this.regDate = regDate;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.approveDate = approveDate;
    }
    public LeaveRequest(int employeeId, Date regDate, String leaveType, Date startDate, Date endDate, String status, Date approveDate) {
        this.employeeId = employeeId;
        this.regDate = regDate;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.approveDate = approveDate;
    }

    public LeaveRequest( Date regDate, String leaveType, Date startDate, Date endDate, Date approveDate,String status) {
        this.regDate = regDate;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approveDate = approveDate;
        this.status = status;
    }
    public LeaveRequest(String status, Date approveDate, int leaveReqId) {
        this.leaveReqId = leaveReqId;
        this.status = status;
        this.approveDate = approveDate;
    }

    public LeaveRequest(int leaveReqId,int employeeId, String name, String supervisor, Date regDate, Date startDate, Date endDate, String leavetype, Date apprDate, String status, String deptDesc ) {
        this.leaveReqId = leaveReqId;
        this.employeeId = employeeId;
        this.name = name;
        this.supervisor = supervisor;
        this.regDate = regDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leavetype;
        this.approveDate = apprDate;
        this.status = status;
        this.deptDesc = deptDesc;

    }

    public LeaveRequest(String name, String supervisor, Date regDate, Date startDate, Date endDate, String leavetype, Date apprDate, String status, String deptDesc, int deptId) {
        this.name = name;
        this.supervisor = supervisor;
        this.regDate = regDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leavetype;
        this.approveDate = apprDate;
        this.status = status;
        this.deptDesc = deptDesc;
        this.deptId = deptId;

    }


    public int getLeaveReqId() {
        return leaveReqId;
    }

    public void setLeaveReqId(int leaveReqId) {
        this.leaveReqId = leaveReqId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
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


    @Override
    public String toString(){
        return leaveReqId + " " + employeeId + " " + regDate + " " + leaveType + " " + startDate + " " + endDate + " " + status + " " + approveDate;
    }




}
