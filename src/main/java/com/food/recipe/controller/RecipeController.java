package com.food.recipe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.model.dto.FilterRecipe;
import com.food.recipe.model.dto.RecipeDTO;
import com.food.recipe.service.RecipeService;

@RestController
@RequestMapping(value = "/v1")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;
	private static final Logger log = LoggerFactory.getLogger(RecipeController.class);

	@GetMapping(path = "/recipes/{id}")
	public ResponseEntity<Object> getRecipe(@PathVariable("id") Long recipeId) {
		log.info("RecipeController :: CALL getRecipie");
		return recipeService.fetchRecipeList(recipeId);
	}

	@GetMapping(path = "/recipes")
	public ResponseEntity<Object> getRecipeFilter(@ModelAttribute final FilterRecipe filterRecipe) {
		log.info("RecipeController :: CALL getRecipeFilter");
		return recipeService.fetchRecipeList(filterRecipe);
	}

	@PostMapping(path = "/recipes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> saveRecipe(@RequestBody @Valid RecipeDTO recipe) {
		log.info("RecipeController :: CALL saveRecipies");
		return recipeService.saveRecipe(recipe);
	}

	@PutMapping("/recipes/{id}")
	public ResponseEntity<Object> updateRecipe(@RequestBody RecipeDTO recipe, @PathVariable("id") Long recipeId) {
		log.info("RecipeController :: CALL updateRecipe");
		return recipeService.updateRecipe(recipe, recipeId);
	}

	@DeleteMapping("/recipes/{id}")
	public ResponseEntity<Object> deleteDepartmentById(@PathVariable("id") Long recipeId) {
		log.info("RecipeController :: CALL deleteDepartmentById");
		return recipeService.deleteRecipeById(recipeId);
	}

}
