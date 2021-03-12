package com.eltavi.recipegram.service;

import com.eltavi.recipegram.entity.FileTable;

public interface FileService {
    void deleteFile(Long stepId);
    FileTable saveFile(FileTable fileTable);
    void deleteFileById(Long id);
}
