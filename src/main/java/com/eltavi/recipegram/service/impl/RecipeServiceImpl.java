package com.eltavi.recipegram.service.impl;

import com.eltavi.recipegram.dto.RecipeDto;
import com.eltavi.recipegram.entity.Recipe;
import com.eltavi.recipegram.entity.User;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.exception.ResourceForbiddenException;
import com.eltavi.recipegram.mapper.RecipeMapper;
import com.eltavi.recipegram.mapper.UserMapper;
import com.eltavi.recipegram.repository.RecipeRepository;
import com.eltavi.recipegram.service.RecipeService;
import com.eltavi.recipegram.service.UserService;
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
    private final UserService userService;
    private final UserMapper userMapper;

    public List<RecipeDto> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::recipeToRecipeDto)
                .collect(Collectors.toList());
    }

    public RecipeDto findDtoById(Long id) {
        return recipeRepository.findById(id)
                .map(recipe ->  {
                    RecipeDto recipeDto = recipeMapper.recipeToRecipeDto(recipe);
                    pullSubscribers(recipe, recipeDto);
                    return recipeDto;
                })
                .orElseThrow(() -> new NotFoundException("There is no recipe with this id"));
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no recipe with this id"));
    }

    public RecipeDto addRecipe(Recipe recipe) {
        RecipeDto recipeDto = recipeMapper.recipeToRecipeDto(recipeRepository.save(recipe));
        pullSubscribers(recipe, recipeDto);
        return recipeDto;
    }

    public boolean deleteRecipe(Long id, Long userId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            if (!recipeOptional.get().getUser().getId().equals(userId)) {
                throw new ResourceForbiddenException("You can't delete other's recipes");
            }
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
            RecipeDto recipeDto = recipeMapper.recipeToRecipeDto(recipeRepository.save(recipe));
            pullSubscribers(recipe, recipeDto);
            return recipeDto;
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
            RecipeDto recipeDto = recipeMapper.recipeToRecipeDto(recipeRepository.save(recipe));
            pullSubscribers(recipe, recipeDto);
            return recipeDto;
        } else {
            throw new NotFoundException("There is no recipe with this id");
        }
    }

    private void pullSubscribers(Recipe recipe, RecipeDto recipeDto) {
        List<User> subscribers = userService.findSubscribers(recipe.getUser());
        recipeDto.getUser()
                .setSubscribers(subscribers.stream()
                        .map(userMapper::userToSubscribeDto)
                        .collect(Collectors.toList()));
    }

}
