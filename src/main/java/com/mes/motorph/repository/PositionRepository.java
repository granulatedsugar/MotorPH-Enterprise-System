package com.mes.motorph.repository;

import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.utils.DBUtility;
import javafx.geometry.Pos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
}
