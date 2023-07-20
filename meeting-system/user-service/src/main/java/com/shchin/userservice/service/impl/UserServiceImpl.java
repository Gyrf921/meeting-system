package com.shchin.userservice.service.impl;

import com.shchin.userservice.dao.User;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.repository.UserRepository;
import com.shchin.userservice.service.UserService;
import com.shchin.userservice.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(long id){
        log.info("[getUserById] >> id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() ->{
                    log.error("User not found for this id :{} ", id);
                    return new ResourceNotFoundException("User not found for this id :: " + id);
                });

        log.info("[getUserById] << result: {}", user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("[getAllUsers] >> without arg");

        List<User> userList = userRepository.findAll();

        log.info("[getAllUsers] << result: {}", userList);
        return userList;
    }


    @Override
    @Transactional
    public User createUser(UserDTO userInfo){
        log.info("[createUser] >> userInfo: {}", userInfo);

        User userSaved = userRepository.save(
                User.builder()
                        .userName(userInfo.getUserName())
                        .build());

        log.info("[createUser] << result: {}", userSaved);
        return userSaved;
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        log.info("[deleteUser] >> id: {}", id);

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            userRepository.deleteById(id);

            log.info("[deleteUser] << result: {} was deleted", userOptional);
        }
    }

    @Override
    @Transactional
    public User updateUserById(Long id, UserDTO userDto) {
        log.info("[updateUserById] >> id: {}, userDto: {}", id, userDto);

        User user = userRepository.findById(id)
                .orElseThrow(() ->{
                    log.error("User not found for this id :{} ", id);
                    return new ResourceNotFoundException("User not found for this id :: " + id);
                });

        user.setUserName(userDto.getUserName());

        User userUpdated = userRepository.save(user);

        log.info("[updateUserById] << result: {}", userUpdated);
        return userUpdated;

    }

    @Override
    public boolean usersIsExist(List<Long> userIdList) {
        log.info("[usersIsExist] >> userIdList: {}", userIdList);

        for (Long userId : userIdList) {
            if (!userRepository.existsById(userId))
            {
                log.info("[usersIsExist] << result: {}", false);
                return false;
            }
        }
        log.info("[usersIsExist] << result: {}", true);
        return true;

    }



}
