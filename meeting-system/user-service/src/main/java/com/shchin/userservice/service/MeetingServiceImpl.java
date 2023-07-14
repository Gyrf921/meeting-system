package com.shchin.userservice.service;

import com.shchin.userservice.dao.MeetingDAO;
import com.shchin.userservice.dao.ParticipantDAO;
import com.shchin.userservice.dao.UserDAO;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.repository.MeetingRepository;
import com.shchin.userservice.repository.ParticipantRepository;
import com.shchin.userservice.web.dto.MeetingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService{

    @Autowired
    private  MeetingRepository meetingRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public Optional<MeetingDAO> findById(Long id) {
        return meetingRepository.findById(id);
    }

    @Override
    public MeetingDAO create(MeetingDTO meetingDTO, Long userOrganizerId){

        log.info("Try to create info about meeting: {}", meetingDTO);

        MeetingDAO meetingDAO = MeetingDAO.builder()
                        .name(meetingDTO.getName())
                        .description(meetingDTO.getDescription())
                        .datetimeStart(meetingDTO.getDatetimeStart())
                        .datetimeEnd(meetingDTO.getDatetimeEnd())
                        .build();


        meetingRepository.save(meetingDAO);

        log.info("User created by user info: {}", meetingDAO);

        log.info("Save organizer in new meeting");
        participantRepository.save(new ParticipantDAO(meetingDAO.getId(), userOrganizerId, true));

        return meetingDAO;
    }

    @Override
    public MeetingDAO update(Long id, MeetingDTO meetingDTO) {

        log.info("Try to update info about meeting number: {}. New information: {}", id, meetingDTO);

        MeetingDAO meetingDAO = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not exist with id :" + id));

        meetingDAO.setName(meetingDTO.getName());
        meetingDAO.setDescription(meetingDTO.getDescription());
        meetingDAO.setDatetimeStart(meetingDTO.getDatetimeStart());
        meetingDAO.setDatetimeEnd(meetingDTO.getDatetimeEnd());

        log.info("Meeting: {} was updated", meetingDAO);

        return meetingRepository.save(meetingDAO);

    }

    @Override
    public void delete(long id) {
        Optional<MeetingDAO> meetingDAO = meetingRepository.findById(id);

        if (meetingDAO.isPresent()) {

            meetingRepository.deleteById(id);

            log.info("User with id: '{}' was deleted", id);
        }
    }
}
