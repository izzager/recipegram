package com.eltavi.recipegram.controller;

import com.eltavi.recipegram.dto.StepDto;
import com.eltavi.recipegram.entity.FileTable;
import com.eltavi.recipegram.entity.Recipe;
import com.eltavi.recipegram.entity.Step;
import com.eltavi.recipegram.exception.BadRequestException;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.exception.ResourceForbiddenException;
import com.eltavi.recipegram.service.FileService;
import com.eltavi.recipegram.service.RecipeService;
import com.eltavi.recipegram.service.StepService;
import com.eltavi.recipegram.service.UserService;
import com.eltavi.recipegram.validator.StepDtoValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StepController {

    private final StepService stepService;
    private final FileService fileService;
    private final RecipeService recipeService;
    private final UserService userService;
    private final StepDtoValidator stepDtoValidator;

    @GetMapping("steps")
    public List<StepDto> showAllSteps() {
        return stepService.findAll();
    }

    @GetMapping("steps/{id}")
    public StepDto showStepById(@PathVariable Long id) {
        return stepService.findDtoById(id);
    }

    @GetMapping("steps/byIdRecipe/{id}")
    public List<StepDto> showStepsByIdRecipe(@PathVariable Long id) {
        return stepService.findByIdRecipe(id);
    }

    @PostMapping("steps")
    public StepDto addStep(Principal auth,
                           @RequestPart(name = "stepDto") StepDto stepDto,
                           @RequestPart(name = "file", required = false) MultipartFile file) {
        stepDtoValidator.validate(stepDto);
        if (file != null) {
            try {
                FileTable fileTable = new FileTable();
                //convert file input stream to byte array, so that we can store it into db
                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                fileTable.setPhotoName(file.getName());
                fileTable.setPhotoContentLength(Long.valueOf(file.getSize()).intValue());
                fileTable.setPhotoContentType(file.getContentType());
                fileTable.setPhotoBlob(bytes);

                Long userId = userService.findByUsername(auth.getName()).getId();
                Recipe recipe = recipeService.findRecipeById(stepDto.getRecipeId());
                if (!recipe.getUser().getId().equals(userId)) {
                    throw new ResourceForbiddenException("You can't change other's recipes");
                }

                fileService.saveFile(fileTable);
                stepDto.setImageStep(fileTable);
                return stepService.save(stepDto);
            } catch (IOException e) {
                throw new BadRequestException("You failed to upload => " + e.getMessage());
            }
        } else {
            throw new BadRequestException("You failed to upload");
        }
    }

    @PatchMapping("steps/{id}")
    public StepDto changeStep(Principal auth,
                              @PathVariable Long id,
                              @RequestPart(name = "stepDto") StepDto stepDto,
                              @RequestPart(name = "file", required = false) MultipartFile file) {
        stepDtoValidator.checkValidUpdateFields(stepDto);
        if (file != null) {
            try {
                FileTable fileTable = new FileTable();
                //convert file input stream to byte array, so that we can store it into db
                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                fileTable.setPhotoName(file.getName());
                fileTable.setPhotoContentLength(Long.valueOf(file.getSize()).intValue());
                fileTable.setPhotoContentType(file.getContentType());
                fileTable.setPhotoBlob(bytes);

                Long userId = userService.findByUsername(auth.getName()).getId();
                Step step = stepService.findStepById(id);
                Recipe recipe = recipeService.findRecipeById(step.getRecipe().getId());
                if (!recipe.getUser().getId().equals(userId)) {
                    throw new ResourceForbiddenException("You can't change other's recipes");
                }

                Long fileId = stepService.deleteFile(id);
                fileService.deleteFileById(fileId);
                fileService.saveFile(fileTable);

                stepDto.setImageStep(fileTable);
                stepDto.setId(id);
                return stepService.changeStep(stepDto);
            } catch (IOException e) {
                throw new BadRequestException("You failed to upload => " + e.getMessage());
            }
        } else {
            stepDto.setId(id);
            return stepService.changeStep(stepDto);
        }
    }

    @DeleteMapping("{id}")
    public void deleteStep(Principal auth,
                           @PathVariable Long id) {
        Long userId = userService.findByUsername(auth.getName()).getId();
        Step step = stepService.findStepById(id);
        Recipe recipe = recipeService.findRecipeById(step.getRecipe().getId());
        if (!recipe.getUser().getId().equals(userId)) {
            throw new ResourceForbiddenException("You can't change other's recipes");
        }

        if (!stepService.deleteStep(id)) {
            throw new NotFoundException("There is no step with this id");
        } else {
            fileService.deleteFile(id);
        }
    }

}
