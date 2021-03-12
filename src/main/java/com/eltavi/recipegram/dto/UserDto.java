package com.eltavi.recipegram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String nickname;

    private String descriptionProfile;

    private List<SubscribeDto> subscriptions;

    private List<SubscribeDto> subscribers;
}
