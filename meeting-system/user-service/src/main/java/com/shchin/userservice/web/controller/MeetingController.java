package com.shchin.userservice.web.controller;

import com.shchin.userservice.dao.MeetingDAO;
import com.shchin.userservice.dao.ParticipantDAO;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.service.*;
import com.shchin.userservice.web.dto.MeetingDTO;
import com.shchin.userservice.web.dto.ParticipantDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/meetingController")
public class MeetingController {
    private final ParticipantService participantService;

    private final MeetingService meetingService;

    public MeetingController(ParticipantServiceImpl participantService, MeetingServiceImpl meetingService) {
        this.participantService = participantService;
        this.meetingService = meetingService;
    }


    @GetMapping("/getInfoAboutMeeting/{id}")
    public ResponseEntity<MeetingDAO> getInfoAboutMeeting(@PathVariable(value = "id") Long id)
    {
        MeetingDAO meetingDAO = meetingService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting with this {} id not exist or not founded"));

        return ResponseEntity.ok().body(meetingDAO);

    }

    @PutMapping("/createMeeting")
    public ResponseEntity<MeetingDAO> createMeeting(@Valid @RequestBody ParticipantDTO participantDTO)
    {
        MeetingDAO meetingDAO = meetingService.create(participantDTO.getMeetingDTO(), participantDTO.getUserOrganizer().getId());

        return ResponseEntity.ok().body(meetingDAO);
    }

    @PostMapping("/updateMeetingInfo/{id}")
    public ResponseEntity<MeetingDAO> updateMeetingInfo(@PathVariable(value = "id") Long id,
                                                        @Valid @RequestBody MeetingDTO meetingDTO)
    {
        MeetingDAO meetingDAO = meetingService.update(id, meetingDTO);

        return ResponseEntity.ok().body(meetingDAO);
    }


    @GetMapping("/getParticipant/{id}")
    public List<ParticipantDAO> getParticipant(@PathVariable(value = "id") Long id)
    {
        return participantService.findAllUsersByMeetingId(id);
    }

    @PutMapping("/createParticipant")
    public List<ParticipantDAO> createParticipant(@Valid @RequestBody ParticipantDTO participantDTO)
    {
        return participantService.create(participantDTO.getMeetingDTO(), participantDTO.getUserDAOList(), participantDTO.getUserOrganizer());
    }

    @PutMapping("/createParticipant/{id}")
    public List<ParticipantDAO> updateParticipant(@PathVariable(value = "id") Long id,
                                                  @Valid @RequestBody ParticipantDTO participantDTO)
    {
        return participantService.addNewUsersInMeeting(id, participantDTO.getUserDAOList());
    }

}
