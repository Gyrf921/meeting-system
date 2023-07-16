package com.shchin.userservice.service;

import com.shchin.userservice.dao.Meeting;
import com.shchin.userservice.dao.Participant;
import com.shchin.userservice.dao.User;
import com.shchin.userservice.repository.ParticipantRepository;
import com.shchin.userservice.service.impl.MeetingServiceImpl;
import com.shchin.userservice.service.impl.ParticipantServiceImpl;
import com.shchin.userservice.service.impl.UserServiceImpl;
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
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {
    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantServiceImpl participantService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private MeetingServiceImpl meetingService;

    private User user;
    private Meeting meeting;
    private MeetingDTO meetingDTO;
    private Participant participant;

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

        participant = Participant.builder()
                .meetingId(meeting.getId())
                .userId(user.getId())
                .isOrganizer(false)
                .build();



    }

    @DisplayName("JUnit test for findAllUsersByMeetingId method")
    @Test
    void findAllUsersByMeetingId() {
        Participant participant_Org = Participant.builder()
                .meetingId(meeting.getId())
                .userId(user.getId())
                .isOrganizer(true)
                .build();

        given(participantRepository.findAllByMeetingId(meeting.getId()))
                .willReturn(List.of(participant, participant_Org));

        List<Participant> participantList = participantService.findAllUsersByMeetingId(meeting.getId());

        System.out.println(participantList);

        // then - verify the output
        assertThat(participantList).isNotNull();
        assertThat(participantList.size()).isEqualTo(2);

    }

    @Test
    void createMeetingWithoutParticipants() {
        User user2 = User.builder()
                .id(112L)
                .userName("userNameForTest2")
                .build();

        Participant participant2 = Participant.builder()
                .meetingId(meeting.getId())
                .userId(user2.getId())
                .isOrganizer(true)
                .build();

        List<Participant> participantList = new LinkedList<>();
        participantList.add(participant);
        participantList.add(participant2);

        given(userService.usersIsExist(List.of(user.getId(), user2.getId())))
                .willReturn(true);
        given(meetingService.createMeetingWithoutParticipants(meetingDTO, user2.getId()))
                .willReturn(meeting);
        given(participantRepository.saveAll(any(LinkedList.class)))
                .willReturn(participantList);

        // when - action or the behaviour that we are going test
        List<Participant> savedPart =
                participantService.createMeetingWithParticipants(meetingDTO, List.of(user.getId(), user2.getId()), user2.getId());
        System.out.println(savedPart);

        // then - verify the output
        assertThat(savedPart).isNotNull();
        assertThat(savedPart.size()).isEqualTo(2);
        assertThat(savedPart.contains(participant2)).isEqualTo(true);
    }

    @Test
    void addNewUsersInMeeting() {
        User user2 = User.builder()
                .id(112L)
                .userName("userNameForTest2")
                .build();

        Participant participant2 = Participant.builder()
                .meetingId(meeting.getId())
                .userId(user2.getId())
                .isOrganizer(false)
                .build();

        //given - precondition or setup
        given(participantRepository.saveAll(any(List.class)))
                .willReturn(List.of(participant, participant2));

        // when - action or the behaviour that we are going test
        List<Participant> savedPart = participantService.addNewUsersInMeeting(meeting.getId(), List.of(user.getId(), user2.getId()));
        System.out.println(savedPart);

        // then - verify the output
        assertThat(savedPart).isNotNull();
        assertThat(savedPart.size()).isEqualTo(2);
        assertThat(savedPart.contains(participant2)).isEqualTo(true);
    }

    @Test
    void delete() {

        willDoNothing().given(participantRepository).deleteById(meeting.getId());

        participantService.deleteMeetingWithParticipant(meeting.getId());

        verify(participantRepository, times(1)).deleteById(meeting.getId());

    }
}