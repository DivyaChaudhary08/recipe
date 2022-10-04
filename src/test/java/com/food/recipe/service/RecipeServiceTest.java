package com.food.recipe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito.BDDMyOngoingStubbing;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.food.recipe.model.dto.RecipeDTO;
import com.food.recipe.model.entity.Ingredient;
import com.food.recipe.model.entity.Recipe;
import com.food.recipe.repository.RecipeRepository;
import com.food.recipe.service.imp.RecipeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private RecipeServiceImpl recipeService;

	private Recipe recipe;
	private RecipeDTO recipeDto;
	ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
	HttpHeaders header = new HttpHeaders();

	@BeforeEach
	public void setup() {
		header.setContentType(MediaType.APPLICATION_JSON);

		recipe = Recipe.builder().id(1L).name("fries").type("veg").serving(4).instructions("oven").ingredients(null)
				.build();

		recipeDto = RecipeDTO.builder().name("poha").type("veg").serving(4).instructions("gas").ingredients(null)
				.build();
		responseEntity = new ResponseEntity<>("created", header, HttpStatus.OK);

	}

	@Test
	public void saveRecipeSucess() {
		given(recipeRepository.save(recipe)).willReturn(recipe);
		ResponseEntity<Object> responseEntity = recipeService.saveRecipe(recipeDto);
		assertThat(responseEntity.getStatusCode().is2xxSuccessful());
	}

	@Test
	public void postRecipeSucess() {
		given(recipeRepository.findById(1L)).willReturn(Optional.empty());
		given(recipeRepository.save(recipe)).willReturn(recipe);
		recipeDto = RecipeDTO.builder().id(1L).name("curry").type("Non veg").serving(4).instructions("oven").ingredients(null)
				.build();
		ResponseEntity<Object> responseEntity = recipeService.updateRecipe(recipeDto,1L);
		assertThat(responseEntity.getStatusCode().is2xxSuccessful());
	}
	@Test
	public void deleteRecipeSucess() {
		willDoNothing().given(recipeRepository).deleteById(1L);
		ResponseEntity<Object> responseEntity = recipeService.deleteRecipeById(1L);
		assertThat(responseEntity.getStatusCode().is2xxSuccessful());
	}


}
