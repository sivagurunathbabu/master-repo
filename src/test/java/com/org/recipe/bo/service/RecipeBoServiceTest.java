package com.org.recipe.bo.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.context.WebApplicationContext;

import com.org.recipe.RecipeManagementApplication;
import com.org.recipe.controller.AbstractTest;
import com.org.recipe.dao.RecipeDAO;
import com.org.recipe.entity.Category;
import com.org.recipe.entity.Recipe;
import com.org.recipe.exception.RecordAlreadyExistsException;
import com.org.recipe.exception.RecordNotFoundException;

@SpringBootTest(classes = RecipeManagementApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class RecipeBoServiceTest extends AbstractTest {

	private static final String OVEN = "oven";
	private static final String POTATO = "Potato";
	private static final String RECIPE_NOT_CREATED_ALREADY_RECEIPE_EXISTS_FOR_MEAT_PEPPER_MASALA = "Recipe not created already receipe exists for Meat Pepper Masala";
	private static final String SALMON = "Salmon";
	private static final String CHILLY = "Chilly";
	private static final String DRUMSTICK = "DRUMSTICK";
	private static final String TOMATO = "Tomato";
	private static final String MEAT = "Meat";
	private static final String PEPPER = "Pepper";
	private static final String SALMON_GRAVY = "Salmon Gravy";
	private static final String SAMBAR = "Sambar";
	private static final String MEAT_PEPPER_MASALA = "Meat Pepper Masala";


	@Mock
	private WebApplicationContext webApplicationContext;

	@Autowired
	private RecipeBoService recipeBoService;
	
	@MockBean
	private RecipeDAO recipeDAO;
	
	@Test
	@DisplayName("create")
	void testCreate() {
		Set<String> ingredients = new HashSet<>();
		ingredients.add(PEPPER);
		ingredients.add(MEAT);
		String instruction = "Marinate meat with Indian spices and fry with oil";

		Category category = Category.NON_VEG;
		int NO_OF_SERVINGS = 2;
		var restaurant = new Recipe(MEAT_PEPPER_MASALA, NO_OF_SERVINGS, ingredients, instruction);
		restaurant.setCategory(category);
		Recipe savedEntity = recipeBoService.create(restaurant);

		assertAll("Receice is created", () -> assertEquals(MEAT_PEPPER_MASALA, savedEntity.getName()),
				() -> assertEquals(instruction, savedEntity.getInstrucions()),
				() -> assertEquals(NO_OF_SERVINGS, savedEntity.getNoOfServings()),
				() -> assertEquals(ingredients, savedEntity.getIngredients()),
				() -> assertEquals(category, savedEntity.getCategory()));

		RecordAlreadyExistsException recordAlreadyExists = assertThrows(RecordAlreadyExistsException.class, () -> {
			recipeBoService.create(restaurant);
		});

		assertEquals(RECIPE_NOT_CREATED_ALREADY_RECEIPE_EXISTS_FOR_MEAT_PEPPER_MASALA,
				recordAlreadyExists.getMessage());

		recipeBoService.delete(MEAT_PEPPER_MASALA);
	}

	@Test
	@DisplayName("update")
	void testUpdate() {
		String instruction = "Boil dal with Indian spices and fry with oil";
		int NO_OF_SERVINGS = 2;
		Set<String> ingredients = new HashSet<>();
		ingredients.add(DRUMSTICK);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		Category category = Category.VEG;

		var sambarRecipe = new Recipe(SAMBAR, NO_OF_SERVINGS, ingredients, instruction);
		sambarRecipe.setCategory(category);
		recipeBoService.create(sambarRecipe);

		String modifiedInstruction = "Boil dal with drumstick , tomato , chilly and Indian spices and serve hot";

		var modifiedSambar = new Recipe(SAMBAR, NO_OF_SERVINGS, ingredients, modifiedInstruction);
		modifiedSambar.setCategory(category);
		Recipe savedEntity = recipeBoService.update(SAMBAR, modifiedSambar);

		assertAll("Receice is update", () -> assertEquals(SAMBAR, savedEntity.getName()),
				() -> assertEquals(modifiedInstruction, savedEntity.getInstrucions()),
				() -> assertEquals(NO_OF_SERVINGS, savedEntity.getNoOfServings()),
				() -> assertEquals(ingredients, savedEntity.getIngredients()),
				() -> assertEquals(category, savedEntity.getCategory()));

		recipeBoService.delete(SAMBAR);

		RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> {
			recipeBoService.update(SAMBAR, modifiedSambar);
		});

		assertEquals("Recipe not updated since no receipe found for Sambar", recordNotFoundException.getMessage());

	}

	@Test
	@DisplayName("Delete")
	void testDelete() {

		String instruction = "Marinate Salmon fish with olive oil , garlic and ginger. After 30 min marination cook in over";
		String SALMON_GRAVY = "Salmon Gravy";
		int NO_OF_SERVINGS = 2;
		Set<String> ingredients = new HashSet<>();
		ingredients.add(SALMON);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		Category category = Category.NON_VEG;

		var salmonRecipe = new Recipe(SALMON_GRAVY, NO_OF_SERVINGS, ingredients, instruction);
		salmonRecipe.setCategory(category);
		var savedEntity = recipeBoService.create(salmonRecipe);

		assertAll("Receice is created", () -> assertEquals(SALMON_GRAVY, savedEntity.getName()),
				() -> assertEquals(instruction, savedEntity.getInstrucions()),
				() -> assertEquals(NO_OF_SERVINGS, savedEntity.getNoOfServings()),
				() -> assertEquals(ingredients, savedEntity.getIngredients()),
				() -> assertEquals(category, savedEntity.getCategory()));

		recipeBoService.delete(SALMON_GRAVY);

		RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> {
			recipeBoService.update(SALMON_GRAVY, salmonRecipe);
		});

		assertEquals("Recipe not updated since no receipe found for Salmon Gravy",
				recordNotFoundException.getMessage());

	}

	@Test
	@DisplayName("allRecipes")
	void testAllrecipes() {
		String instruction = "Marinate Salmon fish with olive oil , garlic and ginger. After 30 min marination cook in over";
		String SALMON_GRAVY = "Salmon Gravy";
		int NO_OF_SERVINGS = 2;
		Set<String> ingredients = new HashSet<>();
		ingredients.add(SALMON);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		Category category = Category.NON_VEG;

		var salmonRecipe = new Recipe(SALMON_GRAVY, NO_OF_SERVINGS, ingredients, instruction);
		salmonRecipe.setCategory(category);
		recipeBoService.create(salmonRecipe);

		List<Recipe> allRecipes = recipeBoService.findAllRecipes();
		assertTrue(allRecipes.size() > 0);
		int count = allRecipes.size();

		recipeBoService.delete(SALMON_GRAVY);
		allRecipes = recipeBoService.findAllRecipes();
		assertTrue(allRecipes.size() > 0);
		assertEquals(--count, allRecipes.size());

	}

	@Test
	@DisplayName("filterRecipes")
	void testFilterRecipes() {
		String instruction = "Marinate Salmon fish with olive oil , garlic and ginger. After 30 min marination cook in oven for 10 min";
		
		final int NO_OF_SERVINGS_4 = 4;
		Set<String> ingredients = new HashSet<>();
		ingredients.add(SALMON);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		Category category = Category.NON_VEG;

		var salmonRecipe = new Recipe(SALMON_GRAVY, NO_OF_SERVINGS_4, ingredients, instruction);
		salmonRecipe.setCategory(category);
		
		recipeBoService.create(salmonRecipe);

		instruction = "Boil dal with Indian spices and fry with oil and reheat with oven when server";
		String SAMBAR = "Sambar";
		
		ingredients = new HashSet<>();
		ingredients.add(DRUMSTICK);
		ingredients.add(TOMATO);
		ingredients.add(CHILLY);
		ingredients.add(POTATO);
		Category vegCategory = Category.VEG;

		var sambarRecipe = new Recipe(SAMBAR, NO_OF_SERVINGS_4, ingredients, instruction);
		sambarRecipe.setCategory(category);
		recipeBoService.create(sambarRecipe);

		List<String> emptyExcludeIngredients=new ArrayList<>();

		List<String> emptyIncludeIngredients=new ArrayList<>();
		
		List<Recipe> nonVegRecipes = recipeBoService.filterRecipes(vegCategory, emptyIncludeIngredients, emptyExcludeIngredients, null, null);
		assertTrue(nonVegRecipes.size() > 0);

		nonVegRecipes.forEach((e) -> {
			assertAll("All are Veg Recipes", () -> assertEquals(Category.VEG, e.getCategory()));
		});

		List<String> potatoIngredients = new ArrayList<>();
		potatoIngredients.add(POTATO);
		
		
		List<Recipe> withPotatoIngredientsAndServingsWith4 = recipeBoService.filterRecipes(null, potatoIngredients, emptyExcludeIngredients, NO_OF_SERVINGS_4, null);

		withPotatoIngredientsAndServingsWith4.forEach((e) -> {
			assertAll("All are potato ingredients and no of servings 4", 
					() -> assertTrue(e.getIngredients().stream().anyMatch(p->p.equals(POTATO))),
					() -> assertEquals(NO_OF_SERVINGS_4,e.getNoOfServings())
					);
		});
		
		List<String> excludeSalmonIngredient = new ArrayList<>();
		excludeSalmonIngredient.add(SALMON);
		
		List<Recipe> withoutSalmonAndInstructionHasOven = recipeBoService.filterRecipes(null, emptyIncludeIngredients, excludeSalmonIngredient, null, OVEN);

		withoutSalmonAndInstructionHasOven.forEach((e) -> {
			assertAll("All are potato ingredients and no of servings 4", 
					() -> assertTrue(e.getIngredients().stream().noneMatch(p->p.equals(SALMON))),
					() -> assertTrue(e.getInstrucions().contains(OVEN))
					);
		});
		
		recipeBoService.delete(SALMON_GRAVY);
		recipeBoService.delete(SAMBAR);

		
	}

}
