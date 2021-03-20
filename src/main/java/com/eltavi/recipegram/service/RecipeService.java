package com.eltavi.recipegram.service;

import com.eltavi.recipegram.dto.RecipeDto;
import com.eltavi.recipegram.entity.Recipe;
import com.eltavi.recipegram.entity.User;

import java.util.List;

public interface RecipeService {
    List<RecipeDto> findAll();
    RecipeDto findDtoById(Long id);
    Recipe findRecipeById(Long id);
    RecipeDto addRecipe(Recipe recipe);
    boolean deleteRecipe(Long id, Long userId);
    RecipeDto like(Long id);
    RecipeDto dislike(Long id);
    List<RecipeDto> getNews(User user, int size);
}
