package com.food.recipe.service;

import org.springframework.http.ResponseEntity;

import com.food.recipe.model.dto.FilterRecipe;
import com.food.recipe.model.dto.RecipeDTO;

public interface RecipeService {
	ResponseEntity<Object> fetchRecipeList(FilterRecipe filterRecipe);
	ResponseEntity<Object> fetchRecipeList(Long recipeId);
	ResponseEntity<Object> fetchRecipeList();

	ResponseEntity<Object> saveRecipe(RecipeDTO recipe) ;

	ResponseEntity<Object> updateRecipe(RecipeDTO recipe, Long recipeId);

	ResponseEntity<Object> deleteRecipeById(Long recipeId);

}
