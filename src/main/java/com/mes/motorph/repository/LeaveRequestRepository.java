package com.mes.motorph.repository;

import com.mes.motorph.entity.LeaveRequest;
import com.mes.motorph.entity.Overtime;
import com.mes.motorph.exception.LeaveRequestException;
import com.mes.motorph.exception.OvertimeException;
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
            String sql = "SELECT * FROM motorph.leaverequest";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int employeeId = rs.getInt("employeeid");
                Date regDate = rs.getDate("reg_date");
                String leaveType = rs.getString("leavetype");
                Date startDate = rs.getDate("startdate");
                Date endDate = rs.getDate("enddate");
                String status = rs.getString("status");
                Date approveDate = rs.getDate("approve_date");

               LeaveRequest leaveRequest = new com.mes.motorph.entity.LeaveRequest(employeeId, regDate, leaveType, startDate, endDate, status, approveDate);
               leaveRequests.add(leaveRequest);
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









}
