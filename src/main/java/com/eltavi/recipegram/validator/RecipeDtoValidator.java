package com.eltavi.recipegram.validator;

import com.eltavi.recipegram.dto.RecipeDto;
import com.eltavi.recipegram.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class RecipeDtoValidator implements DtoValidator<RecipeDto> {

    public void checkValidFields(RecipeDto dto) {
        if (dto.getId() != null || dto.getUser() != null
                || dto.getPublicationDate() != null
                || dto.getLikes() != 0
                || dto.getDislikes() != 0
                || dto.getSteps() != null) {
            throw new BadRequestException("Illegal fields");
        }
    }

    public void checkDifficulty(int difficulty) {
        if (difficulty < 1 || difficulty > 10) {
            throw new BadRequestException("Difficulty should be between 1 and 10");
        }
    }

    @Override
    public void validate(RecipeDto dto) {
        checkValidFields(dto);
        checkDifficulty(dto.getDifficulty());
    }
}
