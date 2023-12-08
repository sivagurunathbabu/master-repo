package com.org.recipe.dao;

import java.util.List;

import com.mongodb.client.result.DeleteResult;
import com.org.recipe.entity.Category;
import com.org.recipe.entity.Recipe;

public interface RecipeDAO {

	public Recipe findByName(String name) ;
	
	public Recipe create(Recipe recipe);

	public List<Recipe> findAllRecipes();

	public Recipe update(Recipe recipe);

	public List<Recipe> filterRecipes(Category category, List<String> ingredients, List<String> ingrediantsExclude,
			Integer noOfServings, String instructions);

	public DeleteResult delete(String name);
}
