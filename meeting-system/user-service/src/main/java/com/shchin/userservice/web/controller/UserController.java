package com.shchin.userservice.web.controller;

import com.shchin.userservice.dao.UserDAO;
import com.shchin.userservice.exception.ResourceNotFoundException;
import com.shchin.userservice.service.UserService;
import com.shchin.userservice.service.UserServiceImpl;
import com.shchin.userservice.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/userController")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDAO> getAllUsers() {
        log.info("Try to show all users");

        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDAO> getUserById(@PathVariable(value = "id") Long id) {
        log.info("Try to show user with id: '{}'", id);

        UserDAO user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users")
    public void createUser(@Valid @RequestBody UserDTO userDto) {
        //Получили запрос на создание пользователя и логируем его
        log.info("Try to create user request received: {}", userDto.toString());

        userService.createUser(userDto);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<UserDAO> updateUser(@PathVariable(value = "id") Long id,
                           @Valid @RequestBody UserDTO userDto) {
        //Получили запрос на обновление пользователя и логируем его
        log.info("Try to update user request received: {}", userDto.toString());

        return ResponseEntity.ok(userService.updateById(id, userDto));

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) {
        log.info("Try to delete user with id: '{}'", id);

        userService.deleteUser(id);
    }
}
