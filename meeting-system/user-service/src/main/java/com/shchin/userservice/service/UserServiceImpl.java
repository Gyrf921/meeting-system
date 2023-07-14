package com.shchin.userservice.service;

import com.shchin.userservice.dao.UserDAO;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.repository.UserRepository;
import com.shchin.userservice.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserDAO> getUserById(long id){
        return userRepository.findById(id);
    }
    @Override
    public List<UserDAO> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public UserDAO createUser(UserDTO userInfo){

        log.info("User created by user info: {}", userInfo);

        return userRepository.save(
                UserDAO.builder()
                        .userName(userInfo.getUserName())
                        .build());


    }

    @Override
    @Transactional
    public void deleteUser(long id) {

        Optional<UserDAO> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            userRepository.deleteById(id);

            log.info("User with id: '{}' was deleted", id);
        }
    }

    @Override
    @Transactional
    public UserDAO updateById(Long id, UserDTO userDto) {
        log.info("Try to update user with this Id: {}. New parameters are {}", id, userDto);

        UserDAO userDAO = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

        userDAO.setUserName(userDto.getUserName());

        log.info("User: {} was updated", userDAO);
        return userRepository.save(userDAO);

    }

    @Override
    public boolean usersIsExist(List<UserDAO> userDAOList) {

        log.info("check list of User on existing: {}", userDAOList);

        for (UserDAO userDAO : userDAOList) {
            if (!userRepository.existsById(userDAO.getId()))
            {
                log.info("User {} isn't exist or wasn't found", userDAO.getUserName());
                return false;
            }
        }
        log.info("all users are existing");
        return true;

    }



}
