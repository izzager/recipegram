package com.eltavi.recipegrampath.repository;

import com.eltavi.recipegrampath.entity.Recipe;
import com.eltavi.recipegrampath.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByUser(User user);
}
