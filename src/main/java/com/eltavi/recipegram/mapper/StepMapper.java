package com.eltavi.recipegram.mapper;

import com.eltavi.recipegram.dto.StepDto;
import com.eltavi.recipegram.entity.Step;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StepMapper {

    @Mapping(source = "step.recipe.id", target = "recipeId")
    StepDto stepToStepDto(Step step);

    @Mapping(target = "recipe.id", source = "stepDto.recipeId")
    Step stepDtoToStep(StepDto stepDto);

}
