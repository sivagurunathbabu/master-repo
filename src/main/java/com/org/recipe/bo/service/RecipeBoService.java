package com.org.recipe.bo.service;

import java.util.List;

import com.org.recipe.entity.Category;
import com.org.recipe.entity.Recipe;

public interface RecipeBoService {
	
	public Recipe  create(Recipe input);
	
	public List<Recipe> findAllRecipes();

	public Recipe update(String name,Recipe recipe);

	public List<Recipe> filterRecipes(Category category,List<String> includedIngredients, List<String> excludedIngredients,
			Integer noOfServings,String instructions);

	public String delete(String name);

}
