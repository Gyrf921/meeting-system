package com.shchin.userservice.service;

import com.shchin.userservice.dao.MeetingDAO;
import com.shchin.userservice.dao.ParticipantDAO;
import com.shchin.userservice.dao.UserDAO;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.repository.MeetingRepository;
import com.shchin.userservice.repository.ParticipantRepository;
import com.shchin.userservice.repository.UserRepository;
import com.shchin.userservice.web.dto.MeetingDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantServiceImpl participantService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private MeetingServiceImpl meetingService;

    private UserDAO userDAO;
    private MeetingDAO meetingDAO;
    private MeetingDTO meetingDTO;
    private ParticipantDAO participantDAO;

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

        participantDAO = ParticipantDAO.builder()
                .meetingId(meetingDAO.getId())
                .userId(userDAO.getId())
                .isOrganizer(false)
                .build();



    }

    @DisplayName("JUnit test for findAllUsersByMeetingId method")
    @Test
    void findAllUsersByMeetingId() {
        ParticipantDAO participantDAO_Org = ParticipantDAO.builder()
                .meetingId(meetingDAO.getId())
                .userId(userDAO.getId())
                .isOrganizer(true)
                .build();

        given(participantRepository.findAllByMeetingId(meetingDAO.getId()))
                .willReturn(List.of(participantDAO, participantDAO_Org));

        List<ParticipantDAO> participantDAOList = participantService.findAllUsersByMeetingId(meetingDAO.getId());

        System.out.println(participantDAOList);

        // then - verify the output
        assertThat(participantDAOList).isNotNull();
        assertThat(participantDAOList.size()).isEqualTo(2);
    }

    @Test
    void create() {
        UserDAO userDAO2 = UserDAO.builder()
                .id(112L)
                .userName("userNameForTest2")
                .build();

        ParticipantDAO participantDAO2 = ParticipantDAO.builder()
                .meetingId(meetingDAO.getId())
                .userId(userDAO2.getId())
                .isOrganizer(true)
                .build();

        List<ParticipantDAO> participantDAOList = new LinkedList<>();
        participantDAOList.add(participantDAO);
        participantDAOList.add(participantDAO2);

        given(userService.usersIsExist(List.of(userDAO, userDAO2)))
                .willReturn(true);
        given(meetingService.create(meetingDTO, userDAO2.getId()))
                .willReturn(meetingDAO);
        when(participantRepository.saveAll(any(LinkedList.class)))
                .thenReturn(participantDAOList);

        // when - action or the behaviour that we are going test
        List<ParticipantDAO> savedPart =
                participantService.create(meetingDTO, List.of(userDAO, userDAO2), userDAO2);
        System.out.println(savedPart);

        // then - verify the output
        assertThat(savedPart).isNotNull();
        assertThat(savedPart.size()).isEqualTo(2);
        assertThat(savedPart.contains(participantDAO2)).isEqualTo(true);
    }

    @Test
    void addNewUsersInMeeting() {
        UserDAO userDAO2 = UserDAO.builder()
                .id(112L)
                .userName("userNameForTest2")
                .build();

        ParticipantDAO participantDAO2 = ParticipantDAO.builder()
                .meetingId(meetingDAO.getId())
                .userId(userDAO2.getId())
                .isOrganizer(false)
                .build();

        //given - precondition or setup
        given(participantRepository.saveAll(any(List.class)))
                .willReturn(List.of(participantDAO, participantDAO2));

        // when - action or the behaviour that we are going test
        List<ParticipantDAO> savedPart = participantService.addNewUsersInMeeting(meetingDAO.getId(), List.of(userDAO , userDAO2));
        System.out.println(savedPart);

        // then - verify the output
        assertThat(savedPart).isNotNull();
        assertThat(savedPart.size()).isEqualTo(2);
        assertThat(savedPart.contains(participantDAO2)).isEqualTo(true);
    }

    @Test
    void delete() {

        willDoNothing().given(participantRepository).deleteById(meetingDAO.getId());

        participantService.delete(meetingDAO.getId());

        verify(participantRepository, times(1)).deleteById(meetingDAO.getId());

    }
}