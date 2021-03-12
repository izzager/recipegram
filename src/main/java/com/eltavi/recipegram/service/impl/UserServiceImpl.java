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
import java.util.Optional;
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

    @Override
    public void subscribe(Long followerId, Long followingId) {
        Optional<User> userOptional = userRepository.findById(followerId);
        if (userOptional.isPresent()) {
            User follower = userOptional.get();
            Optional<User> following = userRepository.findById(followingId);
            if (following.isPresent()) {
                follower.getSubscriptions().add(following.get());
            } else {
                throw new NotFoundException("There is no user with this id");
            }
        } else {
            throw new NotFoundException("There is no user with this id");
        }
    }

    @Override
    public void unsubscribe(Long followerId, Long followingId) {
        Optional<User> userOptional = userRepository.findById(followerId);
        if (userOptional.isPresent()) {
            User follower = userOptional.get();
            Optional<User> following = follower.getSubscriptions()
                    .stream()
                    .filter(user1 -> user1.getId().equals(followingId))
                    .findFirst();
            if (following.isPresent()) {
                follower.getSubscriptions().remove(following.get());
            } else {
                throw new NotFoundException("There is no user with this id");
            }
        } else {
            throw new NotFoundException("There is no user with this id");
        }
    }

}
