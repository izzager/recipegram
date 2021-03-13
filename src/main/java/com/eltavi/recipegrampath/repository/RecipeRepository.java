package com.eltavi.recipegrampath.repository;

import com.eltavi.recipegrampath.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
