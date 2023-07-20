package com.shchin.userservice.service;

import com.shchin.userservice.dao.User;
import com.shchin.userservice.repository.UserRepository;
import com.shchin.userservice.service.impl.UserServiceImpl;
import com.shchin.userservice.web.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(111L)
                .userName("userNameForTest")
                .build();

        userDTO = UserDTO.builder()
                .userName("userDTOForTest")
                .build();
    }


    @DisplayName("JUnit test for getUserById method")
    @Test
    void getUserById() {

        given(userRepository.findById(user.getId()))
                .willReturn(Optional.of(user));


        User receivedUser = userService.getUserById(user.getId());

        System.out.println(receivedUser);

        // then - verify the output
        assertThat(receivedUser).isNotNull();
        assertEquals(receivedUser, user);
    }

    @DisplayName("JUnit test for getAllUsers method")
    @Test
    void getAllUsers() {
        User user2 = User.builder()
                .id(112L)
                .userName("userNameForTest2")
                .build();

        given(userRepository.findAll()).willReturn(List.of(user, user2));


        List<User> userList = userService.getAllUsers();

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for createUser method")
    @Test
    void createUser() {
        //given - precondition or setup
        given(userRepository.save(any(User.class)))
                .willReturn(user);

        System.out.println(userRepository);
        System.out.println(userService);

        // when - action or the behaviour that we are going test
        User savedUser = userService.createUser(userDTO);
        System.out.println(savedUser);

        // then - verify the output
        assertThat(savedUser).isNotNull();
        assertEquals(savedUser, user);
    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    void deleteUser() {
        given(userRepository.findById(user.getId()))
                .willReturn(Optional.of(user));

        willDoNothing().given(userRepository).deleteById(user.getId());

        userService.deleteUser(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());

    }
}