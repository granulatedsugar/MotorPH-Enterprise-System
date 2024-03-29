package com.mes.motorph.services;

import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.repository.AttendanceRepository;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class AttendanceService {
    AttendanceRepository attendanceRepository = new AttendanceRepository();

    public List<Attendance> fetchAttedance() throws AttendanceException{
        return attendanceRepository.fetchAttendance();
    }

    public List<Attendance> fetchAttendaceByEmployeId(int eid, String fDate, String tDate)throws AttendanceException {
        return attendanceRepository.fetchAttendanceByEmployeeId(eid, fDate, tDate);
    }

    public void createAttendance(Attendance attendance) throws AttendanceException{
         attendanceRepository.createAttedance(attendance);
    }

    public void updateAttendance(Attendance attendance) throws AttendanceException{
        attendanceRepository.updateAttendance(attendance);
    }

    public void deleteAttendance(int attendanceId) throws AttendanceException{
        attendanceRepository.deleteAttendance(attendanceId);
    }
    public Attendance fetchAttendanceRecordByDate(int empId, Date date) throws SQLException {
        return attendanceRepository.fetchAttendanceByDate(empId, date);
    }
}
