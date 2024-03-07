package com.mes.motorph.services;

import com.mes.motorph.entity.Overtime;
import com.mes.motorph.exception.OvertimeException;
import com.mes.motorph.repository.OvertimeRepository;

import java.util.List;

public class OvertimeService {
    OvertimeRepository overtimeRepository = new OvertimeRepository();

    public List<Overtime> fetchAllOvertime() throws OvertimeException{
        return overtimeRepository.fetchAllOvertime();
    }

    public void createOvertime(Overtime overtime) throws OvertimeException{
        overtimeRepository.createOvertime(overtime);
    }

    public void updateOvertime(Overtime overtime) throws OvertimeException{
        overtimeRepository.updateOvertime(overtime);
    }

    public void deleteOvertime(int id) throws OvertimeException{
        overtimeRepository.deleteOvertime(id);
    }
}
