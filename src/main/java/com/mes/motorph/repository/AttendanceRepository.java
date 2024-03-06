package com.mes.motorph.repository;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.utils.DBUtility;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.awt.image.DataBufferDouble;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {
    Connection conn = null;
    Statement stmt = null;

    public List<Attendance> fetchAttendance() throws AttendanceException {
        List<Attendance> attendances = new ArrayList<>();

        try{
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();

            String sql = "SELECT * FROM motorph.attendance;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("id");
                int employeeId = rs.getInt("employeeId");
                Date date = rs.getDate("date");
                Time timeIn = rs.getTime("timeIn");
                Time timeOut = rs.getTime("timeOut");

                Attendance attendance = new Attendance(id, employeeId, date, timeIn, timeOut);
                attendances.add(attendance);
            }
            rs.close();
            stmt.close();
        }catch (Exception e){
            throw new AttendanceException("Error Connecting to Database " + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
        return attendances;
    }

    public List<Attendance> fetchAttendanceByEmployeeId(int eid, String fDate, String tDate) throws AttendanceException {
        List<Attendance> eAttendances = new ArrayList<>();

        try{
            conn = DBUtility.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM motorph.employee_hours WHERE `Employee ID` = ? AND `Date` BETWEEN ? AND  ?;");
            pstmt.setInt(1, eid);
            pstmt.setString(2, fDate);
            pstmt.setString(3, tDate);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Log ID");
                Date regDate = rs.getDate("Date");
                int employeeId = rs.getInt("Employee ID");
                String employeeName = rs.getString("Employee Name");
                Time timeIn = rs.getTime("Punch In");
                Time timeOut = rs.getTime("Punch Out");
                double workedHours = rs.getDouble("Worked Hours");
                int regularWorkHours = rs.getInt("Regular Work Hours");
                int overtime = rs.getInt("overtime");
                int late = rs.getInt("late");

                Attendance attendance = new Attendance(id, regDate, employeeId, employeeName, timeIn, timeOut, workedHours, regularWorkHours, overtime, late);
                eAttendances.add(attendance);
            }
            rs.close();
            pstmt.close();
        }catch (Exception e){
            throw new AttendanceException("Error Connecting to Database " + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
        return eAttendances;
    }

    public void createAttedance(Attendance attendance) throws AttendanceException {
        try{
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.attendance(employeeId, date, timeIn, timeOut) VALUES (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, attendance.getEmployeeId());
            pstmt.setDate(2, attendance.getDate());
            pstmt.setTime(3, attendance.getTimeIn());
            pstmt.setTime(4, attendance.getTimeOut());

            int rows = pstmt.executeUpdate();
            if(rows == 0){
                throw new AttendanceException("Failed to add Attendance!");
            }else{
                System.out.println("Attendance Added!");
            }
        }catch (Exception e){
            throw new AttendanceException("Error Connecting to Database " + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }

    }

    //create another method for create,update, delete
    //public class createAttendance(), updateAttendance(), deleteAttendance()
    public void updateAttendance(Attendance attendance) throws  AttendanceException{
        try{
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.attendance SET employeeId=?, date=?, timeIn=?, timeOut=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, attendance.getEmployeeId());
            pstmt.setDate(2, attendance.getDate());
            pstmt.setTime(3,attendance.getTimeIn());
            pstmt.setTime(4, attendance.getTimeOut());
            pstmt.setInt(5, attendance.getId());

            int rows = pstmt.executeUpdate();
            if(rows == 0){
                throw new AttendanceException("Failed to update Attendance!");
            }else{
                System.out.println("Attendance Updated!");
            }
        }catch (Exception e){
            throw new AttendanceException("Error Connecting to Database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void deleteAttendance(int attendanceId) throws AttendanceException{
        try{
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.attendance WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, attendanceId);

            int rows = pstmt.executeUpdate();
            if(rows == 0){
                throw new AttendanceException("Failed to delete Attendance!");
            }else{
                System.out.println("Attendance Deleted!");
            }
        }catch (Exception e){
            throw new AttendanceException("Error Connecting to Database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }

    public Attendance fetchAttendanceByDate(int employeeId, Date date) throws SQLException {
        Attendance attendance = null;
        try{
            conn = DBUtility.getConnection();
            String sql = "SELECT id, employeeId, date, timeIn, timeOut FROM motorph.attendance WHERE employeeId = ? AND date = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            pstmt.setDate(2, new java.sql.Date(date.getTime()));
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                attendance = new Attendance(
                        rs.getInt("id"),
                        rs.getInt("employeeId"),
                        rs.getDate("date"),
                        rs.getTime("timeIn"),
                        rs.getTime("timeOut")
                );
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching attendance record: " + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
        return attendance;
    }


}

