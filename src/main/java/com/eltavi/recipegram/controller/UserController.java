package com.eltavi.recipegram.controller;

import com.eltavi.recipegram.dto.UserDto;
import com.eltavi.recipegram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users")
    public List<UserDto> showAllUsers() {
        return userService.findAll();
    }

    @GetMapping("users/{id}")
    public UserDto showOneUser(@PathVariable Long id) {
        return userService.findDtoById(id);
    }

    @PostMapping("users/{id}/subscribe")
    public void subscribeToUser(@PathVariable Long id) {
        //TODO fix with security
        Long followerId = 1L;
        userService.subscribe(followerId, id);
    }

    @PostMapping("users/{id}/unsubscribe")
    public void unsubscribeFromUser(@PathVariable Long id) {
        //TODO fix with security
        Long followerId = 1L;
        userService.unsubscribe(followerId, id);
    }
}
