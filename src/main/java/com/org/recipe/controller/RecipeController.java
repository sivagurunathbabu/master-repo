package com.org.recipe.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.org.recipe.bo.service.RecipeBoService;
import com.org.recipe.entity.Category;
import com.org.recipe.entity.Recipe;


@RestController
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	public RecipeBoService recipeBoService;

	/**
	 * Shows all the available recipes
	 * @return List<Recipe>
	 */
	@GetMapping("/")
	public List<Recipe> allRecipes() {
		return recipeBoService.findAllRecipes();
	}

	/**
	 * Filters the Recipe based on the supplied criteria
	 * @param Category 
	 * @param IncludedIngredients
	 * @param ExcludedIngredients
	 * @param No Of Servings
	 * @param Instructions
	 * @return All recipes matching the filtering criteria. In case of filter based on ingredients user will provide either IncludedIngredients or ExcludedIngredients 
	 * not both same time. 
	 */
	@GetMapping("/filter")
	public List<Recipe> filter(Category category, String[] includedIngredients, String[] excludedIngredients,
			Integer noOfServings, String instructions) {
		List<String> ingrediantsToInclude = (includedIngredients == null || includedIngredients.length == 0)
				? new ArrayList<>()
				: Arrays.asList(includedIngredients);
		List<String> ingrediantsToExclude = (excludedIngredients == null || excludedIngredients.length == 0)
				? new ArrayList<>()
				: Arrays.asList(excludedIngredients);
		return recipeBoService.filterRecipes(category, ingrediantsToInclude, ingrediantsToExclude, noOfServings,
				instructions);
	}

	/**
	 * @param Recipe
	 * @return
	 */
	@PostMapping("/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Recipe create(@RequestBody Recipe recipe) {
		return recipeBoService.create(recipe);
	}

	@PutMapping("/")
	public Recipe update(String recipeName,@RequestBody Recipe recipe) {
		return recipeBoService.update(recipeName,recipe);
	}

	@DeleteMapping("/{name}")
	public String delete(@PathVariable("name") String name) {
		return recipeBoService.delete(name);
	}

}
