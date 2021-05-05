package com.eltavi.recipegram.repository;

import com.eltavi.recipegram.entity.FileTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileTableRepository extends JpaRepository<FileTable, Long> {
    List<FileTable> findAllByPhotoBlobContains(String searchString);
}
