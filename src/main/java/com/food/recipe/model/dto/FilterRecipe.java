package com.food.recipe.model.dto;

import lombok.Data;

@Data
public class FilterRecipe {

	private String type;
	private Integer serveCounter;
	private String ingredientName;
	private Boolean includeExcludeFlag;
	private String instructions;
}
