package com.eltavi.recipegrampath.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {

    private Long id;

    private UserDto user;

    private String category;

    private String recipeName;

    private int difficulty;

    private String description;

    private int cookTime;

    private LocalDateTime publicationDate;

    private int likes;

    private int dislikes;

    private List<StepDto> steps;
}
