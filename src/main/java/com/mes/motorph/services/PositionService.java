package com.mes.motorph.services;

import com.mes.motorph.entity.Position;
import com.mes.motorph.exception.PositionException;
import com.mes.motorph.repository.PositionRepository;

import java.util.List;

public class PositionService {

    PositionRepository positionRepository = new PositionRepository();

    public List<Position> fetchPositions() throws PositionException {
        return positionRepository.fetchPosition();
    }
}
