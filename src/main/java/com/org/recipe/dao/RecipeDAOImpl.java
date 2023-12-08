package com.org.recipe.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.org.recipe.entity.Category;
import com.org.recipe.entity.Recipe;

@Service
public class RecipeDAOImpl implements RecipeDAO {

	private static final String NAME = "name";
	private static final String CATEGORY = "category";
	private static final String INGREDIENTS = "ingredients";
	private static final String NO_OF_SERVINGS = "noOfServings";
	private static final String INSTRUCIONS = "instrucions";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Recipe create(Recipe recipe) {
		return mongoTemplate.insert(recipe);
	}
	
	public Recipe findByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where(NAME).is(name));
		return mongoTemplate.findOne(query, Recipe.class);
	}
	@Override
	public Recipe update(Recipe recipe) {
		return mongoTemplate.save(recipe);
	}

	@Override
	public List<Recipe> filterRecipes(Category category, List<String> ingredients, List<String> ingrediantsExclude,
			Integer noOfServings, String instructions) {
		Query query = new Query();
		if (category != null) {
			query.addCriteria(Criteria.where(CATEGORY).is(category));
		}
		if (!ingredients.isEmpty()) {
			query.addCriteria(Criteria.where(INGREDIENTS).in(ingredients));
		}
		if (!ingrediantsExclude.isEmpty()) {
			query.addCriteria(Criteria.where(INGREDIENTS).nin(ingrediantsExclude));
		}
		if (noOfServings != null) {
			query.addCriteria(Criteria.where(NO_OF_SERVINGS).is(noOfServings));
		}
		if (instructions != null) {
			query.addCriteria(Criteria.where(INSTRUCIONS).regex(".*?"+instructions+".*"));
		}

		List<Recipe> response = mongoTemplate.find(query, Recipe.class);
		return response;
	}

	@Override
	public DeleteResult delete(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where(NAME).is(name));
		return mongoTemplate.remove(query, Recipe.class);
	}

	@Override
	public List<Recipe> findAllRecipes() {
		return mongoTemplate.findAll(Recipe.class);
	}

}
