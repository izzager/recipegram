package com.eltavi.recipegrampath.service;

import com.eltavi.recipegrampath.entity.FileTable;

public interface FileService {
    void deleteFile(Long stepId);
    FileTable saveFile(FileTable fileTable);
    void deleteFileById(Long id);
}
