package com.shchin.userservice.service.impl;

import com.shchin.userservice.dao.Meeting;
import com.shchin.userservice.dao.Participant;
import com.shchin.userservice.dao.User;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.repository.ParticipantRepository;
import com.shchin.userservice.service.MeetingService;
import com.shchin.userservice.service.ParticipantService;
import com.shchin.userservice.service.UserService;
import com.shchin.userservice.web.dto.MeetingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    private final UserService userService;

    private final MeetingService meetingService;

    private final ParticipantRepository participantRepository;

    public ParticipantServiceImpl(UserServiceImpl userService, MeetingServiceImpl meetingService, ParticipantRepository participantRepository) {
        this.userService = userService;
        this.meetingService = meetingService;
        this.participantRepository = participantRepository;
    }

    @Override
    public List<Participant> findAllUsersByMeetingId(Long meeting_id) {

        log.info("[findAllUsersByMeetingId] >> meeting_id: {}", meeting_id);

        List<Participant> list = participantRepository.findAllByMeetingId(meeting_id);

        log.info("[findAllUsersByMeetingId] << result: {}", list);

        return list;
    }

    @Override
    public List<Participant> createMeetingWithParticipants(MeetingDTO meetingDTO, List<Long> usersIdList, Long userIdOrganizer) {
        log.info("[createMeetingWithParticipants] >> meetingDTO: {}, usersIdList: {}, userIdOrganizer: {}", meetingDTO, usersIdList, userIdOrganizer);

        if (usersIdList.contains(userIdOrganizer)){

            List<Participant> participantList = createMeetingAndAddParticipants(meetingDTO, usersIdList, userIdOrganizer);

            log.info("[createMeetingWithParticipants] << result: {}", participantList);

            return participantList;
        }
        else {
            log.error("Organizer is not founded in usersList");
            throw new ResourceNotFoundException("Organizer is not founded in usersList");
        }
    }

    public List<Participant> createMeetingAndAddParticipants(MeetingDTO meetingDTO, List<Long> usersIdList, Long userIdOrganizer) {
        log.info("[createMeetingAndAddParticipants] >> meetingDTO: {}, usersIdList: {}, userIdOrganizer: {}", meetingDTO, usersIdList, userIdOrganizer);

        if (userService.usersIsExist(usersIdList)) {

            Meeting meeting = meetingService.createMeetingWithoutParticipants(meetingDTO, userIdOrganizer);

            List<Participant> participantList = new LinkedList<>();

            usersIdList.stream()
                    .filter(userId -> !userId.equals(userIdOrganizer))
                    .map(userId -> participantList.add(new Participant(meeting.getId(), userId, false)))
                    .collect(Collectors.toList());

            List<Participant> listParticipant = participantRepository.saveAll(participantList);

            log.info("[createMeetingAndAddParticipants] << result: {}", listParticipant);
            return listParticipant;
        }

        else {
            log.error("Someone user is not founded");
            throw new ResourceNotFoundException("Someone user is not founded");
        }

    }


    @Override
    public List<Participant> addNewUsersInMeeting(Long meetingId, List<Long> userIdList) {
        log.info("[addNewUsersInMeeting] >> meetingId: {}, userIdList: {}", meetingId, userIdList);

        List<Participant> participantList = new LinkedList<>();

        userIdList.stream()
                .map(userId -> participantList.add(new Participant(meetingId, userId, false)))
                .collect(Collectors.toList());

        List<Participant> listParticipant = participantRepository.saveAll(participantList);

        log.info("[addNewUsersInMeeting] << result: {}", listParticipant);

        return listParticipant;
    }

    @Override
    public void deleteMeetingWithParticipant(long id) {
        log.info("[deleteMeetingWithParticipant] >> id: {}", id);

        participantRepository.deleteById(id);

        log.info("[deleteMeetingWithParticipant] << result: {}", id);

    }


}
