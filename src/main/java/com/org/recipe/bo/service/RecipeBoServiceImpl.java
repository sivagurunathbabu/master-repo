package com.org.recipe.bo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.org.recipe.dao.RecipeDAO;
import com.org.recipe.entity.Category;
import com.org.recipe.entity.Recipe;
import com.org.recipe.exception.RecordAlreadyExistsException;
import com.org.recipe.exception.RecordNotFoundException;

@Service
public class RecipeBoServiceImpl implements RecipeBoService {
	
	@Autowired
	private RecipeDAO recipeDAO;

	@Override
	public Recipe create(Recipe recipe) {
		Recipe recipeFound = recipeDAO.findByName(recipe.getName());
		if (recipeFound != null) {
			throw new RecordAlreadyExistsException("Recipe not created already receipe exists for " + recipe.getName());
		}
		return recipeDAO.create(recipe);

	}

	@Override
	public List<Recipe> findAllRecipes() {
		return recipeDAO.findAllRecipes();
	}

	@Override
	public Recipe update(String name,Recipe recipe) {

		Recipe recipeFound = recipeDAO.findByName(name);
		if (recipeFound == null) {
			throw new RecordNotFoundException("Recipe not updated since no receipe found for " + name);
		}
		return recipeDAO.update(recipe);

	}

	@Override
	public List<Recipe> filterRecipes(Category category, List<String> ingredients, List<String> ingrediantsExclude,
			Integer noOfServings, String instructions) {
		
		List<Recipe> recipes = recipeDAO.filterRecipes(category, ingredients, ingrediantsExclude, noOfServings, instructions);
		if(recipes.isEmpty()) {
			throw new RecordNotFoundException("Recipe not found for the given filtering criteria" );
			
		}
		return recipes;
	}

	@Override
	public String delete(String name) {
		Recipe recipeFound = recipeDAO.findByName(name);
		if (recipeFound == null) {
			throw new RecordNotFoundException("Recipe not deleted since no receipe found for " + name);
		}
		DeleteResult delete = recipeDAO.delete(name);
		return  delete.getDeletedCount()>0?"Recipe "+name+" is successfully deleted":"Recipe deletion is zero, Record could be deleted";
	}

}
