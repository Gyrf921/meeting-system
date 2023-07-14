package com.shchin.userservice.service;

import com.shchin.userservice.dao.UserDAO;
import com.shchin.userservice.web.dto.UserDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDAO> getUserById(long id);

    List<UserDAO> getAllUsers();

    @Transactional
    UserDAO createUser(UserDTO userInfo);

    @Transactional
    void deleteUser(long id);

    @Transactional
    UserDAO updateById(Long id, UserDTO userDto);

    boolean usersIsExist(List<UserDAO> userDAOList);

}
