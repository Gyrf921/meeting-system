package com.shchin.userservice.service;

import com.shchin.userservice.dao.User;
import com.shchin.userservice.web.dto.UserDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    User getUserById(long id);

    List<User> getAllUsers();

    @Transactional
    User createUser(UserDTO userInfo);

    @Transactional
    void deleteUser(long id);

    @Transactional
    User updateUserById(Long id, UserDTO userDto);

    boolean usersIsExist(List<Long> userIdList);

}
