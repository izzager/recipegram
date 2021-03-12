package com.eltavi.recipegram.mapper;

import com.eltavi.recipegram.dto.RecipeDto;
import com.eltavi.recipegram.entity.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    //UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    //StepMapper stepMapper = Mappers.getMapper(StepMapper.class);
    
    //@Mapping(source = "user", target = "user", qualifiedByName = "userToDto")
    //@Mapping(source = "steps", target = "steps", qualifiedByName = "stepToDto")
    RecipeDto recipeToRecipeDto(Recipe recipe);

//    @Named("userToDto")
//    static List<UserDto> userToDto(List<User> users) {
//        return users.stream()
//                .map(userMapper::userToUserDto)
//                .collect(Collectors.toList());
//    }
//
//    @Named("stepToDto")
//    static List<StepDto> stepToDto(List<Step> steps) {
//        return steps.stream()
//                .map(stepMapper::stepToStepDto)
//                .collect(Collectors.toList());
//    }

    Recipe recipeDtoToRecipe(RecipeDto recipeDto);
}
