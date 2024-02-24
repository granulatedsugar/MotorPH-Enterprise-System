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
    public void createAttendance(Attendance attendance) throws AttendanceException{
         attendanceRepository.createAttedance(attendance);
    }
    public void updateAttendance(Attendance attendance) throws AttendanceException{
        attendanceRepository.updateAttendance(attendance);
    }

    public void deleteAttendance(int id) throws AttendanceException{
        attendanceRepository.deleteAttendance(id);
    }
}
