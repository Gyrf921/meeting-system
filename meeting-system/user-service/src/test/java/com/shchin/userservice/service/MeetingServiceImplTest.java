package com.shchin.userservice.service;

import com.shchin.userservice.dao.Meeting;
import com.shchin.userservice.dao.Participant;
import com.shchin.userservice.dao.User;
import com.shchin.userservice.repository.MeetingRepository;
import com.shchin.userservice.repository.ParticipantRepository;

import com.shchin.userservice.service.impl.MeetingServiceImpl;
import com.shchin.userservice.web.dto.MeetingDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private User user;

    private Meeting meeting;
    private MeetingDTO meetingDTO;

    private Participant participantWithOrganizerDAO;

    @SneakyThrows
    @BeforeEach
    void setUp() {


        meeting = Meeting.builder()
                .id(111L)
                .name("userNameForTest")
                .description("descriptionForTest")
                .datetimeStart(LocalDate.now())
                .datetimeEnd(LocalDate.now())
                .build();

        meetingDTO = MeetingDTO.builder()
                .name("userNameForTest")
                .description("descriptionForTest")
                .datetimeStart(LocalDate.now())
                .datetimeEnd(LocalDate.now())
                .build();

        user = User.builder()
                .id(111L)
                .userName("userNameForTest")
                .build();

        participantWithOrganizerDAO = Participant.builder()
                .meetingId(meeting.getId())
                .userId(user.getId())
                .isOrganizer(true)
                .build();

    }

    @DisplayName("JUnit test for findById method")
    @Test
    void findById() {
        given(meetingRepository.findById(meeting.getId()))
                .willReturn(Optional.of(meeting));


        Meeting receivedMeeting = meetingService.findById(meeting.getId());

        System.out.println(receivedMeeting);

        // then - verify the output
        assertThat(receivedMeeting).isNotNull();
        assertEquals(receivedMeeting, meeting);
    }

    @Test
    void create() {
        //given - precondition or setup
        given(meetingRepository.save(any(Meeting.class)))
                .willReturn(meeting);

        given(participantRepository.save(any(Participant.class)))
                .willReturn(participantWithOrganizerDAO);

        // when - action or the behaviour that we are going test
        Meeting savedMeeting = meetingService.createMeetingWithoutParticipants(meetingDTO, user.getId());
        System.out.println(savedMeeting);

        // then - verify the output
        assertThat(savedMeeting).isNotNull();
        assertEquals(savedMeeting, meeting);
    }

    @Test
    void update() {
        //given - precondition or setup
        given(meetingRepository.findById(meeting.getId()))
                .willReturn(Optional.of(meeting));

        given(meetingRepository.save(meeting))
                .willReturn(meeting);

        System.out.println(meetingRepository);
        System.out.println(meetingService);

        // when - action or the behaviour that we are going test
        Meeting savedMeeting = meetingService.updateInfoAboutMeeting(meeting.getId() ,meetingDTO);
        System.out.println(savedMeeting);

        // then - verify the output
        assertThat(savedMeeting).isNotNull();
        assertEquals(savedMeeting, meeting);
    }

    @Test
    void delete() {
        given(meetingRepository.findById(meeting.getId()))
                .willReturn(Optional.of(meeting));

        willDoNothing().given(meetingRepository).deleteById(user.getId());

        meetingService.deleteMeetingById(user.getId());

        verify(meetingRepository, times(1)).deleteById(user.getId());

    }
}