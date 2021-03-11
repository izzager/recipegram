package com.eltavi.recipegram.repository;

import com.eltavi.recipegram.entity.Recipe;
import com.eltavi.recipegram.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findAllByRecipeId(Long recipeId);
}
