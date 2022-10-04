package com.food.recipe.util;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.food.recipe.exception.InvalidInputException;
import com.food.recipe.model.dto.Ingredients;

public class CommonUtils {
	private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);
	public static final String BLANK_FIELD = "Field value is blank";

	private CommonUtils() {
	}

	public static void blankFieldCheck(String fieldValue) {
		if (StringUtils.isBlank(fieldValue)) {
			log.error(BLANK_FIELD);
			throw new InvalidInputException();
		}
	}
	public static void isValidIngredientsList(List<Ingredients> fieldValue) {
		if (fieldValue.isEmpty()) {
			log.error(BLANK_FIELD);
			throw new InvalidInputException();
		}
	}
	public static final Predicate<String> nonNullPredicate = Objects::nonNull;
}
