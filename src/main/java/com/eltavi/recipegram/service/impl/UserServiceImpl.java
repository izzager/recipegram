package com.eltavi.recipegram.service.impl;

import com.eltavi.recipegram.dto.UserDto;
import com.eltavi.recipegram.entity.User;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.mapper.UserMapper;
import com.eltavi.recipegram.repository.UserRepository;
import com.eltavi.recipegram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findDtoById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new NotFoundException("There is no user with this id"));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no user with this id"));
    }

    @Override
    public List<User> findSubscribers(User user) {
        return userRepository.findAllBySubscriptions(user);
    }

}
