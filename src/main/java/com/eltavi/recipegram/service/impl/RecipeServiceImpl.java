package com.eltavi.recipegram.service.impl;

import com.eltavi.recipegram.dto.RecipeDto;
import com.eltavi.recipegram.entity.Recipe;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.mapper.RecipeMapper;
import com.eltavi.recipegram.repository.RecipeRepository;
import com.eltavi.recipegram.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public List<RecipeDto> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::recipeToRecipeDto)
                .collect(Collectors.toList());
    }

    public RecipeDto findDtoById(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::recipeToRecipeDto)
                .orElseThrow(() -> new NotFoundException("There is no recipe with this id"));
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no recipe with this id"));
    }

    public RecipeDto addRecipe(Recipe recipe) {
        return recipeMapper.recipeToRecipeDto(recipeRepository.save(recipe));
    }

    public boolean deleteRecipe(Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public RecipeDto like(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            recipe.setLikes(recipe.getLikes() + 1);
            return recipeMapper.recipeToRecipeDto(recipeRepository.save(recipe));
        } else {
            throw new NotFoundException("There is no recipe with this id");
        }
    }

    @Override
    public RecipeDto dislike(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            recipe.setDislikes(recipe.getDislikes() + 1);
            return recipeMapper.recipeToRecipeDto(recipeRepository.save(recipe));
        } else {
            throw new NotFoundException("There is no recipe with this id");
        }
    }

}
