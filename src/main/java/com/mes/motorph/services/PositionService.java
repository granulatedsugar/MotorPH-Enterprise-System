package com.mes.motorph.services;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.repository.PositionRepository;
import javafx.geometry.Pos;

import java.util.List;

public class PositionService {

    PositionRepository positionRepository = new PositionRepository();

    public List<Position> fetchPositions() throws PositionException {
        return positionRepository.fetchPosition();
    }

    public void createPosition(Position position) throws PositionException{
         positionRepository.createPosition(position);
    }

    public void deletePosition(int positionId) throws PositionException{
        positionRepository.deleteAttendance(positionId);
    }

    public void updatePosition(Position position) throws PositionException{
        positionRepository.updateAttendance(position);
    }
}
