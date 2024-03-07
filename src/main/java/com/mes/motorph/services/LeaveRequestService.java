package com.mes.motorph.services;

import com.mes.motorph.entity.LeaveRequest;
import com.mes.motorph.exception.LeaveRequestException;
import com.mes.motorph.repository.LeaveRequestRepository;

import java.util.List;

public class LeaveRequestService {
    LeaveRequestRepository leaveRequestRepository = new LeaveRequestRepository();

    public List<LeaveRequest> fetchAllLeaveRequest() throws LeaveRequestException {
        return leaveRequestRepository.fetchAllLeaveRequest();
    }

    public void createLeaveRequest(LeaveRequest leaveRequest) throws LeaveRequestException {
        leaveRequestRepository.createLeaveRequest(leaveRequest);
    }
}
