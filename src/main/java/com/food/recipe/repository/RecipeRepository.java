package com.food.recipe.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food.recipe.model.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

	@Query(value = "select r from recipe r where r.type=?1")
	Collection<Recipe> findByType(String type);

	@Query(value = "select r from recipe r where r.serving=:serving")
	Collection<Recipe> findByServing(@Param("serving") Integer serving);

}
