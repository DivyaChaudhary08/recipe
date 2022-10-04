package com.food.recipe.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.food.recipe.exception.DataNotFoundException;
import com.food.recipe.model.dto.FilterRecipe;
import com.food.recipe.model.dto.RecipeDTO;
import com.food.recipe.model.entity.Ingredient;
import com.food.recipe.model.entity.Recipe;
import com.food.recipe.repository.IngredientRepository;
import com.food.recipe.repository.RecipeRepository;
import com.food.recipe.repository.specifications.RecipeSpecifications;
import com.food.recipe.service.RecipeService;
import com.food.recipe.util.CommonUtils;

@Service
public class RecipeServiceImpl implements RecipeService {
	private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	public ResponseEntity<Object> fetchRecipeList(Long recipeId) {
		log.info("RecipeServiceImpl :: CALL fetchRecipeList");
		Optional<Recipe> recipeData = recipeRepository.findById(recipeId);
		if (recipeData.isPresent()) {
			RecipeDTO recipeDTO = new RecipeDTO(recipeData.get());
			return new ResponseEntity<>(recipeDTO, HttpStatus.OK);
		} else
			throw new DataNotFoundException();
	}

	@Override
	@Transactional
	public ResponseEntity<Object> saveRecipe(RecipeDTO recipeDTO) {
		log.info("RecipeServiceImpl :: CALL saveRecipe");
		try {
			CommonUtils.blankFieldCheck(recipeDTO.getName());
			Recipe recipe = new Recipe(recipeDTO);
			if (Objects.nonNull(recipe.getIngredients())) {
				List<Ingredient> ingredientList = recipe.getIngredients();
				ingredientList.stream().forEach((c) -> {
					c.setRecipe(recipe);
					saveIngredient(c);
				});
			}
			recipeRepository.save(recipe);
			log.info("RecipeServiceImpl :: saveRecipe Success");
			return new ResponseEntity<>("created", HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("RecipeServiceImpl :: saveRecipe error", e.getMessage());
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}

	}

	public Ingredient saveIngredient(Ingredient ingredient) {
		return ingredientRepository.save(ingredient);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> updateRecipe(RecipeDTO recipeDTO, Long recipeId) {
		log.info("RecipeServiceImpl :: CALL updateRecipe");
		CommonUtils.blankFieldCheck(recipeDTO.getName());
		try {
			Optional<Recipe> recipeDB = recipeRepository.findById(recipeId);// exception handling check optional for db
			if (recipeDB.isPresent()) {
				Recipe recipe = recipeDB.get();
				recipe.setName(recipeDTO.getName());
				recipe.setType(recipeDTO.getType());
				recipe.setServing(recipeDTO.getServing());
				recipe.setInstructions(recipeDTO.getInstructions());
				recipe.setName(recipeDTO.getName());
				recipeRepository.save(recipe);
				log.info("RecipeServiceImpl :: updateRecipe Success");
				return new ResponseEntity<>("Updated", HttpStatus.OK);
			} else {
				log.error("RecipeServiceImpl :: updateRecipe error : no data found");
				throw new DataNotFoundException();
			}
		} catch (NoSuchElementException e) {
			log.error("RecipeServiceImpl :: updateRecipe error", e.getMessage());
			throw new NoSuchElementException();
		}
	}

	@Override
	public ResponseEntity<Object> deleteRecipeById(Long recipeId) {
		log.info("RecipeServiceImpl :: CALL deleteRecipeById");
		try {
			recipeRepository.deleteById(recipeId);
			log.info("RecipeServiceImpl :: deleteRecipeById Success");
			return new ResponseEntity<>("Deleted", HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			log.error("RecipeServiceImpl :: deleteRecipeById error", e.getMessage());
			throw new DataNotFoundException();
		}
	}

	@Override
	public ResponseEntity<Object> fetchRecipeList(FilterRecipe filterRecipe) {
		log.info("RecipeServiceImpl :: CALL fetchRecipeList");
		if (checkFilter(filterRecipe)) {
			Optional<Iterable<Recipe>> recipe = Optional.ofNullable(recipeRepository.findAll());
			if (recipe.isEmpty())
				throw new DataNotFoundException();
			else {
				log.info("RecipeServiceImpl :: fetchRecipeList Success");
				return new ResponseEntity<>(recipe, HttpStatus.OK);
			}
		} else
			return getDatabyFilter(filterRecipe);
	}

	private ResponseEntity<Object> getDatabyFilter(FilterRecipe filterRecipe) {
		Specification<Recipe> specification = null;
		if (Objects.nonNull(filterRecipe.getServeCounter())) {
			specification = RecipeSpecifications.hasServeCount(filterRecipe.getServeCounter());

		}
		if (Objects.nonNull(filterRecipe.getIngredientName())) {
			if (Objects.nonNull(specification))
				specification = specification
						.and(RecipeSpecifications.hasIngredientName(filterRecipe.getIngredientName()));
			else {
				specification = null;
				specification = RecipeSpecifications.hasIngredientName(filterRecipe.getIngredientName());
			}
		}

		if (Objects.nonNull(filterRecipe.getIncludeExcludeFlag())) {

			if (Objects.nonNull(specification))
				specification = specification
						.and(RecipeSpecifications.hasIncludeExclude(filterRecipe.getIncludeExcludeFlag()));
			else {
				specification = null;
				specification = RecipeSpecifications.hasIncludeExclude(filterRecipe.getIncludeExcludeFlag());

			}
		}
		if (Objects.nonNull(filterRecipe.getInstructions())) {

			if (Objects.nonNull(specification))
				specification = specification.and(RecipeSpecifications.hasInstructions(filterRecipe.getInstructions()));
			else {
				specification = null;
				specification = RecipeSpecifications.hasInstructions(filterRecipe.getInstructions());

			}
			specification = specification.and(RecipeSpecifications.hasInstructions(filterRecipe.getInstructions()));
		}
		List<Recipe> recipeFilter = recipeRepository.findAll(specification);
		Set<String> recipeNames = recipeFilter.stream().map(Recipe::getName).collect(Collectors.toSet());
		return returnResponse(recipeNames);

	}

	private ResponseEntity<Object> returnResponse(Set<String> recipeNames) {
		if (recipeNames.isEmpty())
			throw new DataNotFoundException();
		else
			return new ResponseEntity<>(recipeNames, HttpStatus.OK);
	}

	private boolean checkFilter(FilterRecipe filterRecipe) {
		return Optional.ofNullable(filterRecipe.getType()).isEmpty()
				&& Optional.ofNullable(filterRecipe.getServeCounter()).isEmpty()
				&& Optional.ofNullable(filterRecipe.getIngredientName()).isEmpty()
				&& Optional.ofNullable(filterRecipe.getIncludeExcludeFlag()).isEmpty();
	}

	@Override
	public ResponseEntity<Object> fetchRecipeList() {
		Optional<Iterable<Recipe>> recipe = Optional.ofNullable(recipeRepository.findAll());
		if (recipe.isPresent())
			return new ResponseEntity<>(recipe, HttpStatus.OK);
		else
			throw new DataNotFoundException();
	}

}
