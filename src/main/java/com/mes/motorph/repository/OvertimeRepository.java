package com.mes.motorph.repository;

import com.mes.motorph.entity.Overtime;
import com.mes.motorph.exception.OvertimeException;
import com.mes.motorph.utils.DBUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OvertimeRepository {
    Connection conn = null;
    Statement stmt= null;
    PreparedStatement pstmt = null;

    public List<Overtime> fetchAllOvertime() throws OvertimeException{
        List<Overtime> overtimes = new ArrayList<>();
        try{
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();

            String sql = "SELECT * FROM motorph.overtime";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                int employeeId = rs.getInt("employeeid");
                String status = rs.getString("status");

                Overtime overtime = new Overtime(id, date, employeeId, status);
                overtimes.add(overtime);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new OvertimeException("Error Fetching data from the database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
        return overtimes;
    }

    public void createOvertime(Overtime overtime) throws OvertimeException{
        try{
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.overtime(date, employeeid, status) VALUES (?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, overtime.getDate());
            pstmt.setInt(2, overtime.getEmployeeId());
            pstmt.setString(3, overtime.getStatus());

            int rows = pstmt.executeUpdate();

            if(rows == 0){
                System.out.println("Error creating overtime");
            }else{
                System.out.println("Overtime Created");
            }
        }catch (SQLException e){
            throw new OvertimeException("Error connecting to database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void updateOvertime(Overtime overtime) throws OvertimeException{
        try{
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.overtime SET status=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, overtime.getStatus());
            pstmt.setInt(2, overtime.getOvertimeId());

            int rows = pstmt.executeUpdate();

            if(rows == 0){
                System.out.println("Error updating overtime");
            }else{
                System.out.println("Overtime updated");
            }
        }catch (SQLException e){
            throw new OvertimeException("Error connecting to database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }

    public void deleteOvertime(int id) throws OvertimeException{
        try{
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.overtime WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();

            if(rows ==0){
                System.out.println("Error deleting overtime");
            }else{
                System.out.println("Overtime deleted");
            }
        }catch (SQLException e){
            throw new OvertimeException("Error connecting to database" + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }
    }

}
