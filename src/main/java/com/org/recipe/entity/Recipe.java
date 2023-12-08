package com.org.recipe.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Recipe")
public class Recipe {

	@Id
	private String id;
	
	@Indexed(unique = true)
	private String name;
	private Integer noOfServings;
	private Set<String> ingredients=new HashSet<>();
	private String instrucions;
	private Category category;
	public Recipe( String name, int noOfServings, Set<String> ingredients, String instrucions) {
		super();
		this.name = name;
		this.noOfServings = noOfServings;
		this.ingredients = ingredients;
		this.instrucions = instrucions;
	}
	
	public Recipe() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNoOfServings() {
		return noOfServings;
	}
	public void setNoOfServings(int noOfServings) {
		this.noOfServings = noOfServings;
	}
	public Set<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Set<String> ingredients) {
		this.ingredients = ingredients;
	}
	public String getInstrucions() {
		return instrucions;
	}
	public void setInstrucions(String instrucions) {
		this.instrucions = instrucions;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
}
