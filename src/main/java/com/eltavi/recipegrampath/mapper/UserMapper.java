package com.eltavi.recipegrampath.mapper;

import com.eltavi.recipegrampath.dto.SubscribeDto;
import com.eltavi.recipegrampath.dto.UserDto;
import com.eltavi.recipegrampath.entity.User;
import com.eltavi.recipegrampath.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setNickname(user.getNickname());
        userDto.setDescriptionProfile(user.getDescriptionProfile());

        userDto.setSubscriptions(user.getSubscriptions()
                .stream()
                .map(this::userToSubscribeDto)
                .collect(Collectors.toList())
        );

        userDto.setSubscribers(userRepository.findAllBySubscriptions(user)
                .stream()
                .map(this::userToSubscribeDto)
                .collect(Collectors.toList())
        );

        return userDto;
    }

    public SubscribeDto userToSubscribeDto(User user) {
        SubscribeDto subscribeDto = new SubscribeDto();
        subscribeDto.setId(user.getId());
        subscribeDto.setNickname(user.getNickname());
        subscribeDto.setDescriptionProfile(user.getDescriptionProfile());
        return subscribeDto;
    }
}
