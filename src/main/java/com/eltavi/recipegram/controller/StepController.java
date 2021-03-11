package com.eltavi.recipegram.controller;

import com.eltavi.recipegram.dto.StepDto;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StepController {

    private final StepService stepService;

    @GetMapping
    public List<StepDto> showAllSteps() {
        return stepService.findAll();
    }

    @GetMapping("steps/{id}")
    public StepDto showStepById(@PathVariable Long id) {
        return stepService.findById(id);
    }

    @GetMapping("steps/byIdRecipe/{id}")
    public List<StepDto> showStepsByIdRecipe(@PathVariable Long id) {
        return stepService.findByIdRecipe(id);
    }

    @PostMapping("steps")
    public StepDto addStep(@RequestBody StepDto stepDto) {
        return stepService.save(stepDto);
    }

    @PutMapping("steps/{id}")
    public StepDto changeStep(@RequestBody StepDto stepDto) {
        return stepService.changeStep(stepDto);
    }

    @DeleteMapping("{id}")
    public void deleteStep(@PathVariable Long id) {
        if (!stepService.deleteStep(id)) {
            throw new NotFoundException("There is no step with this id");
        }
    }

}
