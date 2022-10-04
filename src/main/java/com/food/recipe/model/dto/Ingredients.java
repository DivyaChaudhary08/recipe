package com.food.recipe.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ingredients {
	private Long id;
	@NotBlank
	@NotNull
	private String name;
	@NotBlank
	@NotNull
	private Boolean optional;

	public Ingredients(Long id, String name, Boolean optional) {
		this.id = id;
		this.name = name;
		this.optional = optional;
	}

}
