package com.eltavi.recipegrampath.service;

import com.eltavi.recipegrampath.dto.RecipeDto;
import com.eltavi.recipegrampath.entity.Recipe;

import java.util.List;

public interface RecipeService {
    List<RecipeDto> findAll();
    RecipeDto findDtoById(Long id);
    Recipe findRecipeById(Long id);
    RecipeDto addRecipe(Recipe recipe);
    boolean deleteRecipe(Long id, Long userId);
    RecipeDto like(Long id);
    RecipeDto dislike(Long id);
}
