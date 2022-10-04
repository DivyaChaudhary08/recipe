package com.food.recipe.repository.specifications;


import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.food.recipe.model.entity.Ingredient;
import com.food.recipe.model.entity.Recipe;

public class RecipeSpecifications {
	
	 public static Specification<Recipe> hasIngredientName(String ingredientName) {
	        return (root, query, criteriaBuilder) -> {
	            Join<Ingredient, Recipe> recipeIngredient = root.join("ingredients");
	            return criteriaBuilder.equal(recipeIngredient.get("name"), ingredientName);
	        };
	    }
	 
	 public static Specification<Recipe> hasIncludeExclude(Boolean includeExclude) {
	        return (root, query, criteriaBuilder) -> {
	            Join<Ingredient, Recipe> recipeIngredient = root.join("ingredients");
	            return criteriaBuilder.equal(recipeIngredient.get("optional"), includeExclude);
	        };
	    }
	 
	 public static Specification<Recipe> hasServeCount(Integer ServeCounter) {
	        return (root, query, criteriaBuilder) ->
	        criteriaBuilder.equal(root.<String>get("serving"), ServeCounter);
	    }
	 
	 public static Specification<Recipe> hasInstructions(String instruction) {
	        return (root, query, criteriaBuilder) ->
	        criteriaBuilder.equal(root.<String>get("instructions"), instruction);
	    }

}


// Recipe Author 
//Ingredient Book
