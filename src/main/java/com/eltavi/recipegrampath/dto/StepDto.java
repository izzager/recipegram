package com.eltavi.recipegrampath.dto;

import com.eltavi.recipegrampath.entity.FileTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepDto {

    private Long id;

    private Long recipeId;

    private int stepNumber;

    private String description;

    private FileTable imageStep;

}