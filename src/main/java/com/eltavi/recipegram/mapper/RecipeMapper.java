package com.eltavi.recipegram.mapper;

import com.eltavi.recipegram.dto.RecipeDto;
import com.eltavi.recipegram.entity.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDto recipeToRecipeDto(Recipe recipe);

    Recipe recipeDtoToRecipe(RecipeDto recipeDto);

}
