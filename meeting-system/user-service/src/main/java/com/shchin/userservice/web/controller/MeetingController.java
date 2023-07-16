package com.shchin.userservice.web.controller;

import com.shchin.userservice.dao.Meeting;
import com.shchin.userservice.dao.Participant;
import com.shchin.userservice.service.*;
import com.shchin.userservice.service.impl.MeetingServiceImpl;
import com.shchin.userservice.service.impl.ParticipantServiceImpl;
import com.shchin.userservice.web.dto.MeetingDTO;
import com.shchin.userservice.web.dto.ParticipantsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/meeting-api")
public class MeetingController {
    private final ParticipantService participantService;

    private final MeetingService meetingService;

    public MeetingController(ParticipantServiceImpl participantService, MeetingServiceImpl meetingService) {
        this.participantService = participantService;
        this.meetingService = meetingService;
    }


    @GetMapping("/meetings/{id}")
    public ResponseEntity<Meeting> getInfoAboutMeeting(@PathVariable(value = "id") Long id)
    {
        log.info("[getInfoAboutMeeting] >> id: {}", id);

        Meeting meeting = meetingService.findById(id);

        log.info("[getInfoAboutMeeting] << result: {}", meeting);

        return ResponseEntity.ok().body(meeting);

    }

    @PutMapping("/meetings")
    public ResponseEntity<Meeting> createMeeting(@Valid @RequestBody ParticipantsDTO participantsDTO)
    {
        log.info("[createMeeting] >> participantsDTO: {}", participantsDTO);

        Meeting meeting = meetingService.createMeetingWithoutParticipants(participantsDTO.getMeetingDTO(), participantsDTO.getUserOrganizerId());

        log.info("[createMeeting] << result: {}", meeting);

        return ResponseEntity.ok().body(meeting);
    }

    @PostMapping("/meetings/{id}")
    public ResponseEntity<Meeting> updateMeetingInfo(@PathVariable(value = "id") Long id,
                                                     @Valid @RequestBody MeetingDTO meetingDTO)
    {
        log.info("[updateMeetingInfo] >> id: {}, meetingDTO: {}", id, meetingDTO);

        Meeting meetingNewInfo = meetingService.updateInfoAboutMeeting(id, meetingDTO);

        log.info("[updateMeetingInfo] << result: {}", meetingNewInfo);

        return ResponseEntity.ok().body(meetingNewInfo);
    }


    @GetMapping("/participants/{id}")
    public List<Participant> getParticipants(@PathVariable(value = "id") Long id)
    {
        log.info("[getParticipants] >> id: {}", id);

        List<Participant> participantList = participantService.findAllUsersByMeetingId(id);

        log.info("[getParticipants] << result: {}", participantList);

        return participantList;
    }

    @PutMapping("/participants")
    public List<Participant> createParticipant(@Valid @RequestBody ParticipantsDTO participantsDTO)
    {
        log.info("[createParticipant] >> participantsDTO: {}", participantsDTO);

        List<Participant> participantList = participantService.createMeetingWithParticipants(participantsDTO.getMeetingDTO(), participantsDTO.getUsersIdList(), participantsDTO.getUserOrganizerId());

        log.info("[createParticipant] << result: {}", participantList);

        return participantList;
    }

    @PostMapping("/participants/{id}")
    public List<Participant> updateParticipant(@PathVariable(value = "id") Long id,
                                               @Valid @RequestBody ParticipantsDTO participantsDTO)
    {
        log.info("[updateParticipant] >> id: {}, participantsDTO: {}", id, participantsDTO);

        List<Participant> participantList = participantService.addNewUsersInMeeting(id, participantsDTO.getUsersIdList());

        log.info("[updateParticipant] << result: {}", participantList);

        return participantList;
    }

}
