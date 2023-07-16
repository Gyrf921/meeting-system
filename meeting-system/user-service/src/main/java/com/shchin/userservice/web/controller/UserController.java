package com.shchin.userservice.web.controller;

import com.shchin.userservice.dao.User;
import com.shchin.userservice.service.UserService;
import com.shchin.userservice.service.impl.UserServiceImpl;
import com.shchin.userservice.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user-api")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        log.info("[getAllUsers] >> no params");

        List<User> userList = userService.getAllUsers();

        log.info("[getAllUsers] << result: {}", userList);
        return userList;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        log.info("[getUserById] >> id: {}", id);

        User user = userService.getUserById(id);

        log.info("[getUserById] << result: {}", user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDto) {
        log.info("[createUser] >> userDto: {}", userDto);

        User userSaved = userService.createUser(userDto);

        log.info("[createUser] >> result: {}", userSaved);

        return ResponseEntity.ok().body(userSaved);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id,
                                           @Valid @RequestBody UserDTO userDto) {
        log.info("[updateUser] >> id: {}, userDto: {}", id, userDto);

        User userUpdated = userService.updateUserById(id, userDto);

        log.info("[updateUser] >> result: {}", userUpdated);

        return ResponseEntity.ok().body(userUpdated);

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) {
        log.info("[deleteUser] >> id: {}", id);

        userService.deleteUser(id);

        log.info("[deleteUser] >> result: users was deleted");
    }
}
