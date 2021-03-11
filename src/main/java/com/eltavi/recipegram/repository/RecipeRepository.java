package com.eltavi.recipegram.repository;

import com.eltavi.recipegram.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
