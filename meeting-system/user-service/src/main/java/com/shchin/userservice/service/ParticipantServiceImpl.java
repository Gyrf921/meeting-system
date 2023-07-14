package com.shchin.userservice.service;

import com.shchin.userservice.dao.MeetingDAO;
import com.shchin.userservice.dao.ParticipantDAO;
import com.shchin.userservice.dao.UserDAO;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.repository.ParticipantRepository;
import com.shchin.userservice.web.dto.MeetingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ParticipantServiceImpl implements ParticipantService{

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public List<ParticipantDAO> findAllUsersByMeetingId(Long meeting_id) {
        return participantRepository.findAllByMeetingId(meeting_id);
    }

    @Override
    public List<ParticipantDAO> create(MeetingDTO meetingDTO, List<UserDAO> userDAOList, UserDAO userOrganizer) {

        log.info("try to create List<ParticipantDAO> with a lot of users with this meeting info: {} | this users {} | and this userOrganizer {}", meetingDTO, userDAOList, userOrganizer);

        if (userDAOList.contains(userOrganizer)){

            if (userService.usersIsExist(userDAOList)) {

                MeetingDAO meetingDAO = meetingService.create(meetingDTO, userOrganizer.getId());

                List<ParticipantDAO> participantDAOList = new LinkedList<>();
                for (UserDAO user : userDAOList) {
                    if (!user.equals(userOrganizer)) {
                        participantDAOList.add(new ParticipantDAO(meetingDAO.getId(), user.getId(), false));
                    }
                }

                log.info("Try to save new users: {} in this meeting {}", userDAOList, meetingDTO);
                return participantRepository.saveAll(participantDAOList);
            }
            else {
                throw new ResourceNotFoundException("Someone user is not founded");
            }
        }
        else {
            throw new ResourceNotFoundException("Organizer is not founded in usersList");
        }
    }


    @Override
    public List<ParticipantDAO> addNewUsersInMeeting(Long meetingId, List<UserDAO> userDAOList) {
        log.info("Try to add new users in meeting number: {} | These users {}", meetingId, userDAOList);

        List<ParticipantDAO> participantDAOList = new LinkedList<>();
        for (UserDAO user : userDAOList) {
            participantDAOList.add(new ParticipantDAO(meetingId, user.getId(), false));
        }

        log.info("Try to add new users: {} in this meeting {}", userDAOList, meetingId);

        return participantRepository.saveAll(participantDAOList);
    }

    @Override
    public void delete(long id) {

        participantRepository.deleteById(id);

    }


}
