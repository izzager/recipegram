package com.eltavi.recipegram.service;

import com.eltavi.recipegram.dto.RecipeDto;
import com.eltavi.recipegram.entity.Recipe;

import java.util.List;

public interface RecipeService {
    List<RecipeDto> findAll();
    RecipeDto findDtoById(Long id);
    Recipe findRecipeById(Long id);
    RecipeDto addRecipe(Recipe recipe);
    boolean deleteRecipe(Long id);
    RecipeDto like(Long id);
    RecipeDto dislike(Long id);
}
