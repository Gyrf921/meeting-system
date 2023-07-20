package com.shchin.userservice.service;

import com.shchin.userservice.dao.Participant;
import com.shchin.userservice.dao.User;
import com.shchin.userservice.web.dto.MeetingDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface ParticipantService {
   // List<Participant> findAllByMeeting_id(Long id);

    @Transactional
    List<Participant> createMeetingWithParticipants(MeetingDTO meetingDTO, List<Long> usersIdList, Long userIdOrganizer);

    @Transactional
    List<Participant> addNewUsersInMeeting(Long meetingId, List<Long> userIdList);

    @Transactional
    void deleteMeetingWithParticipant(long id);

    @Transactional
    List<Participant> findAllUsersByMeetingId(Long meetingId);
}
