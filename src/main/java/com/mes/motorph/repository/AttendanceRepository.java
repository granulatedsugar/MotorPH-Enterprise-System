package com.mes.motorph.repository;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.exception.PayrollException;
import com.mes.motorph.utils.DBUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

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

}
