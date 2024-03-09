package com.mes.motorph.repository;

import com.mes.motorph.entity.LeaveRequest;
import com.mes.motorph.exception.LeaveRequestException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestRepository {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    public List<LeaveRequest> fetchAllLeaveRequest() throws LeaveRequestException{
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        try{
            conn= DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT concat(e.firstname,\" \", e.lastname ) AS 'employee name', e.supervisor AS 'direct superior' ,lr.reg_date AS 'submitted date', lr.startdate, lr.enddate, lr.leavetype, lr.approve_date, lr.status, d.dept_desc, lr.leavereqid, lr.employeeid FROM motorph.leaverequest lr JOIN motorph.employee e ON lr.employeeid = e.id  JOIN motorph.department d ON d.dept_id = e.deptId";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String name = rs.getString("employee name");
                String supervisor = rs.getString("direct superior");
                Date regDate = rs.getDate("submitted date");
                Date startDate = rs.getDate("startdate");
                Date endDate = rs.getDate("enddate");
                String leavetype = rs.getString("leavetype");
                Date apprDate = rs.getDate("approve_date");
                String status = rs.getString("status");
                String deptDesc = rs.getString("dept_desc");
                int leavereqid = rs.getInt("leavereqid");
                int employeeid = rs.getInt("employeeid");

                LeaveRequest leaveReq = new LeaveRequest(leavereqid,employeeid, name,supervisor, regDate, startDate, endDate,leavetype,apprDate,status, deptDesc);
                leaveRequests.add(leaveReq);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtility.closeConnection(conn);
        }
        return  leaveRequests;
    }

    public void createLeaveRequest(LeaveRequest leaveRequest) throws LeaveRequestException {
        try{
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.leaverequest(employeeid, reg_date, leavetype, startdate, enddate, status, approve_date) VALUES (?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, leaveRequest.getEmployeeId());
            pstmt.setDate(2, leaveRequest.getRegDate());
            pstmt.setString(3, leaveRequest.getLeaveType());
            pstmt.setDate(4, leaveRequest.getStartDate());
            pstmt.setDate(5, leaveRequest.getEndDate());
            pstmt.setString(6, leaveRequest.getStatus());
            pstmt.setDate(7, leaveRequest.getApproveDate());

            int rows = pstmt.executeUpdate();

            if(rows == 0){
                System.out.println("Error creating leave request");
            }else{
                System.out.println("Leave Request Created");
            }
        }catch (SQLException e){
            throw new LeaveRequestException("Error connecting to database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }
    public void updateLeaveRequest(LeaveRequest leaveRequest) throws LeaveRequestException{
        try{
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.leaverequest SET status=?, approve_date =? WHERE leavereqid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, leaveRequest.getStatus());
            pstmt.setDate(2, leaveRequest.getApproveDate());
            pstmt.setInt(3, leaveRequest.getLeaveReqId());

            int rows = pstmt.executeUpdate();

            if(rows == 0){
                System.out.println("Error updating leave request");
            }else{
                System.out.println("Leave request updated");
            }
        }catch (SQLException e){
            throw new LeaveRequestException("Error connecting to database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void deleteLeaveRequest(int leaveReqId) throws LeaveRequestException{
        try{
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.leaverequest WHERE leavereqid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, leaveReqId);

            int rows = pstmt.executeUpdate();

            if(rows ==0){
                System.out.println("Error deleting leave request");
            }else{
                System.out.println("Leave request deleted");
            }
        }catch (SQLException e){
            throw new LeaveRequestException("Error connecting to database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }

    public List<LeaveRequest> fetchLeaveRequestBySupervisor(int deptid) throws LeaveRequestException{
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        try{
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT concat(e.firstname,\" \", e.lastname ) AS 'employee name', e.supervisor AS 'direct superior' ,lr.reg_date AS 'submitted date', lr.startdate, lr.enddate, lr.leavetype, lr.approve_date, lr.status, d.dept_desc FROM motorph.leaverequest lr JOIN motorph.employee e ON lr.employeeid = e.id  JOIN motorph.department d ON d.dept_id = e.deptId WHERE dept_id=?";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String name = rs.getString("employee name");
                String supervisor = rs.getString("direct superior");
                Date regDate = rs.getDate("submitted date");
                Date startDate = rs.getDate("startdate");
                Date endDate = rs.getDate("enddate");
                String leavetype = rs.getString("leavetype");
                Date apprDate = rs.getDate("approve_date");
                String status = rs.getString("status");
                String deptDesc = rs.getString("dept_desc");
                int deptId = rs.getInt("deptId");

                LeaveRequest leaveReq = new LeaveRequest(name, supervisor, regDate, startDate, endDate,leavetype,apprDate,status, deptDesc,deptId);
                leaveRequests.add(leaveReq);
            }
            rs.close();
            stmt.close();
        }catch (SQLException e){
            throw new LeaveRequestException("Error connecting to database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
        return leaveRequests;
    }

    public  List<LeaveRequest> fetchAllLeaveRequestByEmpId(int empId) throws LeaveRequestException {
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        try{
            conn = DBUtility.getConnection();
            String sql = "SELECT * FROM motorph.leaverequest WHERE employeeid=?";
            PreparedStatement newPstmt = conn.prepareStatement(sql);
            newPstmt.setInt(1, empId);
            ResultSet rs = newPstmt.executeQuery();

            while(rs.next()){
                Date regDate = rs.getDate("reg_date");
                String leavetype = rs.getString("leavetype");
                Date startDate = rs.getDate("startdate");
                Date endDate = rs.getDate("enddate");
                Date apprDate = rs.getDate("approve_date");
                String status = rs.getString("status");

                LeaveRequest leaveRequest = new LeaveRequest(regDate, leavetype, startDate, endDate,apprDate, status);
                leaveRequests.add(leaveRequest);
            }
            rs.close();
            newPstmt.close();
        } catch (SQLException e) {
            throw new LeaveRequestException("Error Connecting to the database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
        return leaveRequests;
    }

}
