package com.eltavi.recipegram.service;

import com.eltavi.recipegram.dto.UserDto;
import com.eltavi.recipegram.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();
    UserDto findDtoById(Long id);
    User findUserById(Long id);
    List<User> findSubscribers(User user);
}
