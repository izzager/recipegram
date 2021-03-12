package com.eltavi.recipegram.repository;

import com.eltavi.recipegram.entity.FileTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileTableRepository extends JpaRepository<FileTable, Long> {
}
