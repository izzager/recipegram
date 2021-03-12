package com.eltavi.recipegram.controller;

import com.eltavi.recipegram.dto.StepDto;
import com.eltavi.recipegram.entity.FileTable;
import com.eltavi.recipegram.exception.BadRequestException;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.service.FileService;
import com.eltavi.recipegram.service.StepService;
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

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StepController {

    private final StepService stepService;
    private final FileService fileService;

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
    public StepDto addStep(@RequestPart(name = "stepDto") StepDto stepDto,
                           @RequestPart(name = "file") MultipartFile file) {
        System.out.println(stepDto);
        if (file != null) {
            try {
                FileTable fileTable = new FileTable();
                //convert file input stream to byte array, so that we can store it into db
                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                fileTable.setPhotoName(file.getName());
                fileTable.setPhotoContentLength(Long.valueOf(file.getSize()).intValue());
                fileTable.setPhotoContentType(file.getContentType());
                fileTable.setPhotoBlob(bytes);
                fileService.saveFile(fileTable);
                stepDto.setImageStep(fileTable);
                return stepService.save(stepDto);
            } catch (Exception e) {
                throw new BadRequestException("You failed to upload => " + e.getMessage());
            }
        } else {
            throw new BadRequestException("You failed to upload");
        }
    }

    @PatchMapping("steps/{id}")
    public StepDto changeStep(@PathVariable Long id,
                              @RequestPart(name = "stepDto") StepDto stepDto,
                              @RequestPart(name = "file", required = false) MultipartFile file) {
        if (file != null) {
            try {
                FileTable fileTable = new FileTable();
                //convert file input stream to byte array, so that we can store it into db
                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                fileTable.setPhotoName(file.getName());
                fileTable.setPhotoContentLength(Long.valueOf(file.getSize()).intValue());
                fileTable.setPhotoContentType(file.getContentType());
                fileTable.setPhotoBlob(bytes);
                //TODO fix links
                fileService.deleteFile(id);
                stepService.deleteFile(id);
                fileService.saveFile(fileTable);

                stepDto.setImageStep(fileTable);
                stepDto.setId(id);
                return stepService.changeStep(stepDto);
            } catch (Exception e) {
                throw new BadRequestException("You failed to upload => " + e.getMessage());
            }
        } else {
            return stepService.changeStep(stepDto);
        }
    }

    @DeleteMapping("{id}")
    public void deleteStep(@PathVariable Long id) {
        if (!stepService.deleteStep(id)) {
            throw new NotFoundException("There is no step with this id");
        } else {
            fileService.deleteFile(id);
        }
    }

}
