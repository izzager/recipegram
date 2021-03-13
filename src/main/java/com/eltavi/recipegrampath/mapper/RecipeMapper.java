package com.eltavi.recipegrampath.mapper;

import com.eltavi.recipegrampath.dto.RecipeDto;
import com.eltavi.recipegrampath.entity.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDto recipeToRecipeDto(Recipe recipe);

    Recipe recipeDtoToRecipe(RecipeDto recipeDto);

}
