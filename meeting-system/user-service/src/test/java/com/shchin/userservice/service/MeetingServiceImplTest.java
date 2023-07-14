package com.shchin.userservice.service;

import com.shchin.userservice.dao.MeetingDAO;
import com.shchin.userservice.dao.ParticipantDAO;
import com.shchin.userservice.dao.UserDAO;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.repository.MeetingRepository;
import com.shchin.userservice.repository.ParticipantRepository;

import java.text.SimpleDateFormat;
import com.shchin.userservice.web.dto.MeetingDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MeetingServiceImplTest {

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private MeetingServiceImpl meetingService;

    private UserDAO userDAO;

    private MeetingDAO meetingDAO;
    private MeetingDTO meetingDTO;

    private ParticipantDAO participantWithOrganizerDAO;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "2023-07-11";

        meetingDAO = MeetingDAO.builder()
                .id(111L)
                .name("userNameForTest")
                .description("descriptionForTest")
                .datetimeStart(formatter.parse(dateInString))
                .datetimeEnd(formatter.parse(dateInString))
                .build();

        meetingDTO = MeetingDTO.builder()
                .name("userNameForTest")
                .description("descriptionForTest")
                .datetimeStart(formatter.parse(dateInString))
                .datetimeEnd(formatter.parse(dateInString))
                .build();

        userDAO = UserDAO.builder()
                .id(111L)
                .userName("userNameForTest")
                .build();

        participantWithOrganizerDAO = ParticipantDAO.builder()
                .meetingId(meetingDAO.getId())
                .userId(userDAO.getId())
                .isOrganizer(true)
                .build();

    }

    @DisplayName("JUnit test for findById method")
    @Test
    void findById() {
        given(meetingRepository.findById(meetingDAO.getId()))
                .willReturn(Optional.of(meetingDAO));


        Optional<MeetingDAO> receivedMeeting = Optional.ofNullable(meetingService.findById(meetingDAO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("MeetingDAO is not found")));

        System.out.println(receivedMeeting);

        // then - verify the output
        assertThat(receivedMeeting).isNotNull();
    }

    @Test
    void create() {
        //given - precondition or setup
        given(meetingRepository.save(any(MeetingDAO.class)))
                .willReturn(meetingDAO);

        given(participantRepository.save(any(ParticipantDAO.class)))
                .willReturn(participantWithOrganizerDAO);

        // when - action or the behaviour that we are going test
        MeetingDAO savedMeeting = meetingService.create(meetingDTO, userDAO.getId());
        System.out.println(savedMeeting);

        // then - verify the output
        assertThat(savedMeeting).isNotNull();
    }

    @Test
    void update() {
        //given - precondition or setup
        given(meetingRepository.findById(meetingDAO.getId()))
                .willReturn(Optional.of(meetingDAO));

        given(meetingRepository.save(meetingDAO))
                .willReturn(meetingDAO);

        System.out.println(meetingRepository);
        System.out.println(meetingService);

        // when - action or the behaviour that we are going test
        MeetingDAO savedMeeting = meetingService.update(meetingDAO.getId() ,meetingDTO);
        System.out.println(savedMeeting);

        // then - verify the output
        assertThat(savedMeeting).isNotNull();
    }

    @Test
    void delete() {
        given(meetingRepository.findById(meetingDAO.getId()))
                .willReturn(Optional.of(meetingDAO));

        willDoNothing().given(meetingRepository).deleteById(userDAO.getId());

        meetingService.delete(userDAO.getId());

        verify(meetingRepository, times(1)).deleteById(userDAO.getId());

    }
}