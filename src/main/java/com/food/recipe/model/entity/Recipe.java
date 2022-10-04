package com.food.recipe.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.food.recipe.model.dto.Ingredients;
import com.food.recipe.model.dto.RecipeDTO;
import com.food.recipe.util.CommonUtils;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "recipe")
@Data
@NoArgsConstructor
@Builder
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String type;
	private Integer serving;
	private String instructions;
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Ingredient> ingredients;
	

	public Recipe(RecipeDTO recipeDTO) {
		this.name = recipeDTO.getName();
		this.type = recipeDTO.getType();
		this.serving = recipeDTO.getServing();
		this.instructions = recipeDTO.getInstructions();
		List<Ingredients> ingredientList = recipeDTO.getIngredients();
		if(Objects.nonNull(ingredientList))
		{
		List<Ingredient> ingredientListvalue = ingredientList.stream()
				.map(value -> new Ingredient(value.getName(), value.getOptional())).collect(Collectors.toList());
		ingredients = new ArrayList<>();
		ingredients.addAll(ingredientListvalue);
		}
	}

	public Recipe(String type, Integer serving) {
		this.type = type;
		this.serving = serving;
	}

	public Recipe(Long id, String name, String type, Integer serving,String instructions, List<Ingredient> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.serving = serving;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}

}
