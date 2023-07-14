package com.shchin.userservice.service;

import com.shchin.userservice.dao.MeetingDAO;
import com.shchin.userservice.dao.ParticipantDAO;
import com.shchin.userservice.dao.UserDAO;
import com.shchin.userservice.web.dto.MeetingDTO;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ParticipantService {
   // List<ParticipantDAO> findAllByMeeting_id(Long id);

    @Transactional
    List<ParticipantDAO> create(MeetingDTO meetingDTO, List<UserDAO> userDAOList, UserDAO userOrganizer);

    @Transactional
    List<ParticipantDAO> addNewUsersInMeeting(Long id, List<UserDAO> userDAOList);

    @Transactional
    void delete(long id);

    @Transactional
    List<ParticipantDAO> findAllUsersByMeetingId(Long meetingId);
}
