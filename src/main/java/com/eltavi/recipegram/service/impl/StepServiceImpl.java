package com.eltavi.recipegram.service.impl;

import com.eltavi.recipegram.dto.StepDto;
import com.eltavi.recipegram.entity.Step;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.mapper.StepMapper;
import com.eltavi.recipegram.repository.StepRepository;
import com.eltavi.recipegram.service.RecipeService;
import com.eltavi.recipegram.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;
    private final RecipeService recipeService;
    private final StepMapper stepMapper;

    public List<StepDto> findAll() {
        return stepRepository.findAll()
                .stream()
                .map(stepMapper::stepToStepDto)
                .collect(Collectors.toList());
    }

    public StepDto findById(Long id) {
        return stepRepository.findById(id)
                .map(stepMapper::stepToStepDto)
                .orElseThrow(() -> new NotFoundException("There is no step with this id"));
    }

    public List<StepDto> findByIdRecipe(Long recipeId) {
        return stepRepository.findAllByRecipeId(recipeId)
                .stream()
                .map(stepMapper::stepToStepDto)
                .collect(Collectors.toList());
    }

    public StepDto save(StepDto stepDto) {
        Step step = stepMapper.stepDtoToStep(stepDto);
        return stepMapper.stepToStepDto(stepRepository.save(step));
    }

    public StepDto changeStep(StepDto stepDto) {
        if (!stepRepository.existsById(stepDto.getId())) {
            throw new NotFoundException("There is no step with this id");
        }
        Step step = stepMapper.stepDtoToStep(stepDto);
        step.setRecipe(recipeService.findRecipeById(stepDto.getRecipeId()));
        return stepMapper.stepToStepDto(stepRepository.save(step));
    }

    public boolean deleteStep(Long id) {
        if (stepRepository.existsById(id)) {
            stepRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
