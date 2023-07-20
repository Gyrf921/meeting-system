package com.shchin.userservice.service.impl;

import com.shchin.userservice.dao.Meeting;
import com.shchin.userservice.dao.Participant;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.repository.MeetingRepository;
import com.shchin.userservice.repository.ParticipantRepository;
import com.shchin.userservice.service.MeetingService;
import com.shchin.userservice.web.dto.MeetingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    private final ParticipantRepository participantRepository;

    public MeetingServiceImpl(MeetingRepository meetingRepository, ParticipantRepository participantRepository) {
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public Meeting findById(Long id) {
        log.info("[findById] >> id: {}", id);

        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Meeting with this id : {}, not exist or not founded", id);
                    return new ResourceNotFoundException("Meeting not exist or not founded");
                });

        log.info("[findById] << result: {}", meeting);
        return meeting;
    }

    @Override
    public Meeting createMeetingWithoutParticipants(MeetingDTO meetingDTO, Long userOrganizerId){
        log.info("[createMeetingWithoutParticipants] >> meetingDTO: {}, userOrganizerId: {}", meetingDTO, userOrganizerId);

        Meeting meeting = Meeting.builder()
                        .name(meetingDTO.getName())
                        .description(meetingDTO.getDescription())
                        .datetimeStart(meetingDTO.getDatetimeStart())
                        .datetimeEnd(meetingDTO.getDatetimeEnd())
                        .build();

        log.info("[meetingRepository.save] >> meeting: {}", meeting);
        Meeting resultmeeting = meetingRepository.save(meeting);

        Participant participant = new Participant(meeting.getId(), userOrganizerId, true);
        log.info("[participantRepository.save] >> participant: {}", participant);
        participantRepository.save(participant);

        log.info("[createMeetingWithoutParticipants] << result: {}", participant);
        return resultmeeting;
    }

    @Override
    public Meeting updateInfoAboutMeeting(Long id, MeetingDTO meetingDTO) {
        log.info("[updateInfoAboutMeeting] >> id: {}, meetingDTO: {}", id, meetingDTO);

        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Meeting not exist with id: {}", id);
                    return new ResourceNotFoundException("Meeting not exist with id :" + id);
                });

        meeting.setName(meetingDTO.getName());
        meeting.setDescription(meetingDTO.getDescription());
        meeting.setDatetimeStart(meetingDTO.getDatetimeStart());
        meeting.setDatetimeEnd(meetingDTO.getDatetimeEnd());

        Meeting meetingSaved = meetingRepository.save(meeting);

        log.info("[updateInfoAboutMeeting] << result: {}", meetingSaved);

        return meetingSaved;
    }

    @Override
    public void deleteMeetingById(Long id) {
        log.info("[deleteMeetingById] >> id: {}", id);
        Optional<Meeting> meeting = meetingRepository.findById(id);

        if (meeting.isPresent()) {

            meetingRepository.deleteById(id);

            log.info("[deleteMeetingById] << result: {}", id);
        }
    }
}
