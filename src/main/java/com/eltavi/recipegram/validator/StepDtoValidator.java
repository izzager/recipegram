package com.eltavi.recipegram.validator;

import com.eltavi.recipegram.dto.StepDto;
import com.eltavi.recipegram.entity.Step;
import com.eltavi.recipegram.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class StepDtoValidator implements DtoValidator<StepDto> {

    public void checkValidFields(StepDto dto) {
        if (dto.getId() != null || dto.getImageStep() != null) {
            throw new BadRequestException("Illegal fields");
        }
    }

    public void checkValidUpdateFields(StepDto dto) {
        if (dto.getId() != null || dto.getRecipeId() != null
                || dto.getStepNumber() != 0
                || dto.getImageStep() != null) {
            throw new BadRequestException("Illegal fields");
        }
    }

    @Override
    public void validate(StepDto dto) {
        checkValidFields(dto);
    }
}
