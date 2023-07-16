package com.shchin.userservice.service;

import com.shchin.userservice.dao.Meeting;
import com.shchin.userservice.web.dto.MeetingDTO;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MeetingService {

    Meeting findById(Long id);

    @Transactional
    Meeting createMeetingWithoutParticipants(MeetingDTO meetingDTO, Long userOrganizerId);

    @Transactional
    Meeting updateInfoAboutMeeting(Long id, MeetingDTO meetingDTO);

    @Transactional
    void deleteMeetingById(Long id);

}
