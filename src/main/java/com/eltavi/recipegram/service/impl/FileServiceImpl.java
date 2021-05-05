package com.eltavi.recipegram.service.impl;

import com.eltavi.recipegram.entity.FileTable;
import com.eltavi.recipegram.exception.NotFoundException;
import com.eltavi.recipegram.repository.FileTableRepository;
import com.eltavi.recipegram.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<FileTable> findAllFilesWithSubstring(String searchString) {
        return fileRepository.findAllByPhotoBlobContains(searchString);
    }
}
