package com.eltavi.recipegrampath.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeDto {

    private Long id;

    private String nickname;

    private String descriptionProfile;

}
