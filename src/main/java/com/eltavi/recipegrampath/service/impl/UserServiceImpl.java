package com.eltavi.recipegrampath.service.impl;

import com.eltavi.recipegrampath.dto.UserDto;
import com.eltavi.recipegrampath.entity.User;
import com.eltavi.recipegrampath.exception.BadRequestException;
import com.eltavi.recipegrampath.exception.NotFoundException;
import com.eltavi.recipegrampath.mapper.UserMapper;
import com.eltavi.recipegrampath.repository.UserRepository;
import com.eltavi.recipegrampath.service.UserService;
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
                if (follower.getSubscriptions().contains(following.get())) {
                    throw new BadRequestException("You can't subscribe user twice");
                }
                follower.getSubscriptions().add(following.get());
                userRepository.save(follower);
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
                userRepository.save(follower);
            } else {
                throw new NotFoundException("You are not following this user");
            }
        } else {
            throw new NotFoundException("There is no user with this id");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("There is no user with this username"));
    }

}
