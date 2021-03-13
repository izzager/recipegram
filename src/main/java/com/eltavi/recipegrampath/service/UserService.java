package com.eltavi.recipegrampath.service;

import com.eltavi.recipegrampath.dto.UserDto;
import com.eltavi.recipegrampath.entity.User;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto findDtoById(Long id);
    User findUserById(Long id);
    List<User> findSubscribers(User user);
    void subscribe(Long followerId, Long followingId);
    void unsubscribe(Long followerId, Long followingId);
    User findByUsername(String username);
}
