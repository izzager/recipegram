package com.eltavi.recipegram.repository;

import com.eltavi.recipegram.entity.Recipe;
import com.eltavi.recipegram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByUser(User user);
}
