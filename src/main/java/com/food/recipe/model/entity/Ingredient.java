package com.food.recipe.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Entity(name="ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Boolean optional;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = true)
	private Recipe recipe;

	public Ingredient(String name, Boolean optional) {
		this.name = name;
		this.optional = optional;
	}
	public Ingredient(String name) {
		this.name = name;
	}
	public Ingredient(Long id, String name, Boolean optional, Recipe recipe) {
		super();
		this.id = id;
		this.name = name;
		this.optional = optional;
		this.recipe = recipe;
	}
	
}
