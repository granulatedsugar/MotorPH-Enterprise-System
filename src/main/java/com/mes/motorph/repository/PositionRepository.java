package com.mes.motorph.repository;

import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.utils.AlertUtility;
import com.mes.motorph.utils.DBUtility;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionRepository {

    Connection conn = null;
    Statement stmt = null;

    public List<Position> fetchPosition() throws PositionException {
        List<Position> positions = new ArrayList<>();

        try {
            conn = DBUtility.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM motorph.`position`";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("positionId");
                String title = rs.getString("title");

                Position position = new Position(id, title);
                positions.add(position);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new PositionException("Error connecting to database: " + e.getMessage(),e);
        } finally {
             DBUtility.closeConnection(conn);
        }
        return positions;
    }

    public void createPosition(Position position) throws PositionException{
        try {
            conn = DBUtility.getConnection();
            String sql = "INSERT INTO motorph.position(title) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, position.getTitle());

            int rows = pstmt.executeUpdate();

            if (rows == 0) {
                throw new PositionException("Failed to create new Position");
            } else {
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Position", null, "Added a new Position!");
            }

        }catch (SQLException e) {
            throw new RuntimeException("Error Connecting to Database " + e.getMessage(), e);
        }finally {
            DBUtility.closeConnection(conn);
        }

    }

    public void deletePosition(int id) throws PositionException{
        try{
            conn = DBUtility.getConnection();
            String sql = "DELETE FROM motorph.position WHERE positionId =?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();

            if(rows == 0){
                throw new PositionException("Failed to delete Position");
            }else{
                System.out.println("Position Deleted");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateAttendance(Position position) throws PositionException{
        try {
            conn = DBUtility.getConnection();
            String sql = "UPDATE motorph.position SET title =? WHERE positionId=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, position.getTitle());
            pstmt.setInt(2, position.getPositionId());
            int rows = pstmt.executeUpdate();

            if(rows == 0){
                System.out.println("Failed to update Position");
            }else{

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
