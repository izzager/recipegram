package com.eltavi.recipegrampath.mapper;

import com.eltavi.recipegrampath.dto.StepDto;
import com.eltavi.recipegrampath.entity.Step;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StepMapper {

    @Mapping(source = "step.recipe.id", target = "recipeId")
    StepDto stepToStepDto(Step step);

    @Mapping(target = "recipe.id", source = "stepDto.recipeId")
    Step stepDtoToStep(StepDto stepDto);

}
