package com.eltavi.recipegrampath.service.impl;

import com.eltavi.recipegrampath.entity.FileTable;
import com.eltavi.recipegrampath.exception.NotFoundException;
import com.eltavi.recipegrampath.repository.FileTableRepository;
import com.eltavi.recipegrampath.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileTableRepository fileRepository;
    private static final String ROOT_PATH = "D:\\recipegram\\";

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
        List<FileTable> files = fileRepository.findAll();
        List<FileTable> result = new ArrayList<>();
        String line;
        for (FileTable file : files) {
            String filePath = file.getPhotoPath();
            try {
                FileReader fileReader = new FileReader(filePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(searchString)) {
                        result.add(file);
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println(filePath + "not found");
            }
        }
        return result;
    }
}
