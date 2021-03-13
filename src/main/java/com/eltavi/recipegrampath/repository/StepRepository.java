package com.eltavi.recipegrampath.repository;

import com.eltavi.recipegrampath.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findAllByRecipeId(Long recipeId);
}
