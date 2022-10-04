package com.food.recipe.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.food.recipe.model.entity.Ingredient;
import com.food.recipe.model.entity.Recipe;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
public class RecipeDTO {
	public RecipeDTO(Long id, @NotNull @NotBlank String name, @NotBlank @NotNull String type,
			@NotBlank @NotNull Integer serving, @NotBlank(groups = Ingredients.class) List<Ingredients> ingredients,
			@NotBlank @NotNull String instructions) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.serving = serving;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}

	private Long id;
	@NotNull
	@NotBlank
	private String name;
	@NotBlank
	@NotNull
	private String type;
	@NotBlank
	@NotNull
	private Integer serving;
	@NotBlank(groups = Ingredients.class)
	private List<Ingredients> ingredients;
	@NotBlank
	@NotNull
	private String instructions;

	public RecipeDTO(Recipe recipe) {
		this.name = recipe.getName();
		this.type = recipe.getType();
		this.serving = recipe.getServing();
		this.instructions = recipe.getInstructions();
		List<Ingredient> ingredientList = recipe.getIngredients();
		if(Objects.nonNull(ingredientList))
		{
			List<Ingredients> ingredientListvalue = ingredientList.stream()
					.map(value -> new Ingredients(value.getId(), value.getName(), value.getOptional()))
					.collect(Collectors.toList());
			ingredients = new ArrayList<Ingredients>();
			ingredients.addAll(ingredientListvalue);
		}
		

	}

}
