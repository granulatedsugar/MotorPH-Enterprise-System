package com.mes.motorph.services;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.repository.AttendanceRepository;

import java.util.List;

public class AttendanceService {
    AttendanceRepository attendanceRepository = new AttendanceRepository();

    public List<Attendance> fetchAttedance() throws AttendanceException{
        return attendanceRepository.fetchAttendance();
    }
}
