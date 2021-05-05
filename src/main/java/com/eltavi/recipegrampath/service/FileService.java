package com.eltavi.recipegrampath.service;

import com.eltavi.recipegrampath.entity.FileTable;

import java.util.List;

public interface FileService {
    void deleteFile(Long stepId);
    FileTable saveFile(FileTable fileTable);
    void deleteFileById(Long id);
    List<FileTable> findAllFilesWithSubstring(String searchString);
}
