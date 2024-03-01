package com.mes.motorph.repository;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.utils.DBUtility;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

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
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, attendance.getEmployeeId());
            stmt.setDate(2, attendance.getDate());
            stmt.setTime(3, attendance.getTimeIn());
            stmt.setTime(4, attendance.getTimeOut());

            int rows = stmt.executeUpdate();
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
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, attendance.getEmployeeId());
            stmt.setDate(2, attendance.getDate());
            stmt.setTime(3,attendance.getTimeIn());
            stmt.setTime(4, attendance.getTimeOut());
            stmt.setInt(5, attendance.getId());

            int rows = stmt.executeUpdate();
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

    public void deleteAttendance(int positionId) throws AttendanceException{
        try{
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.attendance WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, positionId);

            int rows = stmt.executeUpdate();
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




}
