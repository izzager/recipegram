package com.eltavi.recipegrampath.controller;

import com.eltavi.recipegrampath.dto.StepDto;
import com.eltavi.recipegrampath.entity.FileTable;
import com.eltavi.recipegrampath.entity.Recipe;
import com.eltavi.recipegrampath.entity.Step;
import com.eltavi.recipegrampath.exception.BadRequestException;
import com.eltavi.recipegrampath.exception.NotFoundException;
import com.eltavi.recipegrampath.exception.ResourceForbiddenException;
import com.eltavi.recipegrampath.service.FileService;
import com.eltavi.recipegrampath.service.RecipeService;
import com.eltavi.recipegrampath.service.StepService;
import com.eltavi.recipegrampath.service.UserService;
import com.eltavi.recipegrampath.validator.StepDtoValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StepController {

    private static final String ROOT_PATH = "D:\\recipegram\\";

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
            Long userId = userService.findByUsername(auth.getName()).getId();
            Recipe recipe = recipeService.findRecipeById(stepDto.getRecipeId());
            if (!recipe.getUser().getId().equals(userId)) {
                throw new ResourceForbiddenException("You can't change other's recipes");
            }

            FileTable fileTable = new FileTable();
            loadFile(fileTable, file);
            fileService.saveFile(fileTable);
            stepDto.setImageStep(fileTable);
            return stepService.save(stepDto);
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
        checkUserRights(auth, id);
        if (file != null) {
            FileTable fileTable = new FileTable();
            loadFile(fileTable, file);
            Long fileId = stepService.deleteFile(id);
            fileService.deleteFileById(fileId);
            fileService.saveFile(fileTable);

            stepDto.setImageStep(fileTable);
        }
        stepDto.setId(id);
        return stepService.changeStep(stepDto);
    }

    @DeleteMapping("steps/{id}")
    public void deleteStep(Principal auth,
                           @PathVariable Long id) {
        checkUserRights(auth, id);
        Step step = stepService.findStepById(id);
        stepService.deleteStep(id);
        fileService.deleteFile(step.getImageStep().getId());
    }

    private void checkUserRights(Principal auth, Long id) {
        Long userId = userService.findByUsername(auth.getName()).getId();
        Step step = stepService.findStepById(id);
        Recipe recipe = recipeService.findRecipeById(step.getRecipe().getId());
        if (!recipe.getUser().getId().equals(userId)) {
            throw new ResourceForbiddenException("You can't change other's recipes");
        }
    }

    private void loadFile(FileTable fileTable, MultipartFile file) {
        try {
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            File dir = new File(ROOT_PATH + File.separator + "loadFiles");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filePath = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
            File uploadedFile = new File(filePath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
            stream.write(bytes);
            stream.flush();
            stream.close();
            fileTable.setPhotoName(file.getOriginalFilename());
            fileTable.setPhotoPath(filePath);
        } catch (IOException e) {
            throw new BadRequestException("You failed to upload => " + e.getMessage());
        }
    }

    @PostMapping("loadData")
    public void loadData(@RequestPart(name = "stepDto") StepDto stepDto,
                         @RequestPart(name = "file") MultipartFile file) {
        stepDtoValidator.validate(stepDto);

        for (int i = 0; i < 1000; i++) {
            FileTable fileTable = new FileTable();
            loadFile(fileTable, file);
            fileService.saveFile(fileTable);

            StepDto savedDto = new StepDto();
            savedDto.setDescription(stepDto.getDescription());
            savedDto.setRecipeId(stepDto.getRecipeId());
            savedDto.setImageStep(fileTable);
            stepService.save(savedDto);
        }
    }

    @GetMapping("steps/search")
    public List<FileTable> findBySearchString(@RequestParam String searchString) {
        return fileService.findAllFilesWithSubstring(searchString);
    }

    @GetMapping("steps/searchByDescription")
    public List<StepDto> findStepBySearchString(@RequestParam String searchString) {
        return stepService.findAllStepsByDescription(searchString);
    }

}
