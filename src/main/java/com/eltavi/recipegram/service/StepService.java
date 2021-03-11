package com.eltavi.recipegram.service;

import com.eltavi.recipegram.dto.StepDto;
import com.eltavi.recipegram.entity.Step;

import java.util.List;

public interface StepService {
    List<StepDto> findAll();
    StepDto findById(Long id);
    List<StepDto> findByIdRecipe(Long recipeId);
    StepDto save(StepDto stepDto);
    boolean deleteStep(Long id);
    StepDto changeStep(StepDto stepDto);
}
