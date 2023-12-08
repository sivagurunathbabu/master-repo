package com.org.recipe.controller;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.recipe.RecipeManagementApplication;
import com.org.recipe.bo.service.RecipeBoService;
import com.org.recipe.entity.Recipe;


@SpringBootTest(classes = RecipeManagementApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class RecipeControllerTest extends AbstractTest {



	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private RecipeBoService recipeBoService;

	private static final String MEAT = "Meat";
	private static final String PEPPER = "Pepper";
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeMethod
	@BeforeClass
	protected void setUp() throws IllegalAccessException {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGetallRecipesReturn200() throws Exception {
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/recipe/");
		MockHttpServletResponse response = mvc.perform(req.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
				.getResponse();
		assertAll("Get all Recipe", () -> assertEquals(200, response.getStatus()));
		
		
	}

	@Test
	public void testFilter() throws Exception {
		
		mvc.perform(get("/recipe/filter")
				.param("category", "NON_")
	            .contentType("application/json"))
	            .andExpect(status().isBadRequest());
		
		mvc.perform(get("/recipe/filter")
				.param("category", "NON_VEG")
	            .contentType("application/json"))
	            .andExpect(status().isOk());

		mvc.perform(get("/recipe/filter")
		            .param("includedIngredients", PEPPER,MEAT)
		            .contentType("application/json"))
		            .andExpect(status().isOk());
		
		mvc.perform(get("/recipe/filter")
	            .param("excludedIngredients", PEPPER,MEAT)
	            .contentType("application/json"))
	            .andExpect(status().isOk());
		
		mvc.perform(get("/recipe/filter")
	            .param("instructions", PEPPER,MEAT)
	            .contentType("application/json"))
	            .andExpect(status().isOk());
		
		
	}
	
	@Test
	public void testCreateReturn201() throws JsonProcessingException, Exception {
		
		Set<String> ingredients = new HashSet<>();
		ingredients.add(PEPPER);
		ingredients.add(MEAT);
		String instruction = "Marinate meat with Indian spices and fry with oil";

		String MEAT_PEPPER_MASALA = "Meat Pepper Masala";
		int NO_OF_SERVINGS = 2;
		Recipe recipe = new Recipe(MEAT_PEPPER_MASALA, NO_OF_SERVINGS, ingredients, instruction);
		
		mvc.perform(post("/recipe/")
		            .content(objectMapper.writeValueAsString(recipe))
		            .contentType("application/json"))
		            .andExpect(status().isCreated());
	}
	
	
	@Test
	public void testUpdateReturn200() throws JsonProcessingException, Exception {
		
		Set<String> ingredients = new HashSet<>();
		ingredients.add(PEPPER);
		ingredients.add(MEAT);
		String instruction = "Marinate meat with Indian spices and fry with oil";

		String MEAT_PEPPER_MASALA = "Meat Pepper Masala";
		int NO_OF_SERVINGS = 2;
		Recipe recipe = new Recipe(MEAT_PEPPER_MASALA, NO_OF_SERVINGS, ingredients, instruction);
		
		mvc.perform(put("/recipe/")
		            .param("name", MEAT_PEPPER_MASALA)
					.content(objectMapper.writeValueAsString(recipe))
		            .contentType("application/json"))
		            .andExpect(status().isOk());
	}
	
	
	@Test
	public void testDeleteReturn200() throws JsonProcessingException, Exception {
		String MEAT_PEPPER_MASALA = "Meat Pepper Masala";
		mvc.perform(delete("/recipe/{name}",MEAT_PEPPER_MASALA)
		            .contentType("application/json"))
		            .andExpect(status().isOk());
	}
}
