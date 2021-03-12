package com.eltavi.recipegram.service.impl;

import com.eltavi.recipegram.entity.FileTable;
import com.eltavi.recipegram.entity.Step;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.repository.FileTableRepository;
import com.eltavi.recipegram.service.FileService;
import com.eltavi.recipegram.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileTableRepository fileRepository;
    private final StepService stepService;

    @Override
    public void deleteFile(Long stepId) {
        Step step = stepService.findStepById(stepId);
        if (step.getImageStep() != null) {
            fileRepository.deleteById(step.getImageStep().getId());
        } else {
            throw new NotFoundException("There is no file");
        }
    }

    @Override
    public FileTable saveFile(FileTable fileTable) {
        return fileRepository.save(fileTable);
    }
}
