package com.eltavi.recipegram.controller;

import com.eltavi.recipegram.dto.RecipeDto;
import com.eltavi.recipegram.entity.Recipe;
import com.eltavi.recipegram.entity.User;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.mapper.RecipeMapper;
import com.eltavi.recipegram.service.RecipeService;
import com.eltavi.recipegram.service.UserService;
import com.eltavi.recipegram.validator.RecipeDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;
    private final RecipeMapper recipeMapper;
    private final RecipeDtoValidator recipeDtoValidator;

    @GetMapping("recipes")
    public List<RecipeDto> showAllRecipes() {
        return recipeService.findAll();
    }

    @GetMapping("recipes/{id}")
    public RecipeDto showRecipeById(@PathVariable Long id) {
        return recipeService.findDtoById(id);
    }

    @PostMapping("recipes")
    public RecipeDto addRecipe(@RequestBody RecipeDto recipeDto) {
        recipeDtoValidator.validate(recipeDto);
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        User owner = userService.findUserById(recipeDto.getUserId());
        recipe.setUser(owner);
        return recipeService.addRecipe(recipe);
    }

    @DeleteMapping("recipes/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        if (!recipeService.deleteRecipe(id)) {
            throw new NotFoundException("There is no recipe with this id");
        }
    }

}
