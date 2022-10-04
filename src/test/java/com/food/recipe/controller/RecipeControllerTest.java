package com.food.recipe.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.recipe.model.dto.RecipeDTO;
import com.food.recipe.model.entity.Recipe;
import com.food.recipe.service.imp.RecipeServiceImpl;

@WebMvcTest()
public class RecipeControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
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
		responseEntity = new ResponseEntity<>(recipe, header, HttpStatus.OK);
		recipeDto = RecipeDTO.builder().name("poha").type("veg").serving(4).instructions("gas").ingredients(null)
				.build();
	}

	@Test
	void fetchByIdSuccess() throws Exception {
		mockMvc.perform(get("/v1/recipes/1")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void fetchByIdCheckPayloadSuccess() throws Exception {
		when(recipeService.fetchRecipeList(1L)).thenReturn(responseEntity);
		System.out.println(recipe);
		mockMvc.perform(get("/v1/recipes/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("fries"));
	}

	@Test
	void fetchByIdCheckPayloadFailure() throws Exception {
		header.setContentType(MediaType.APPLICATION_JSON);
		responseEntity = new ResponseEntity<>(null, header, HttpStatus.INTERNAL_SERVER_ERROR);
		when(recipeService.fetchRecipeList(81L)).thenReturn(responseEntity);
		mockMvc.perform(get("/v1/recipes/81")).andDo(print()).andExpect(status().is5xxServerError());

	}

	@Test
	void insertRecipeSuccess() throws Exception {
	when(recipeService.saveRecipe(recipeDto)).thenReturn(responseEntity);
	mockMvc.perform(post("/v1/recipes").
		content("{\n"
				+ "    \"type\": \"veg\",\n"
				+ "    \"name\": \"fries\",\n"
				+ "    \"serving\": \"4\",\n"
				+ "    \"instructions\": \"oven\",\n"
				+ "    \"ingredients\": [\n"
				+ "    ]\n"
				+ "}")) 
		.andDo(print()).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
