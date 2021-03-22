package com.eltavi.recipegrampath.service.impl;

import com.eltavi.recipegrampath.entity.FileTable;
import com.eltavi.recipegrampath.exception.NotFoundException;
import com.eltavi.recipegrampath.repository.FileTableRepository;
import com.eltavi.recipegrampath.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileTableRepository fileRepository;

    @Override
    public void deleteFile(Long id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
        } else {
            throw new NotFoundException("There is no file");
        }
    }

    @Override
    public void deleteFileById(Long id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
        } else {
            throw new NotFoundException("There is no file");
        }
    }

    @Override
    public FileTable saveFile(FileTable fileTable) {
        return fileRepository.save(fileTable);
    }
}
