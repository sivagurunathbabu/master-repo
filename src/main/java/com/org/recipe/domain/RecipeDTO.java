package com.org.recipe.domain;

import java.util.List;

public class RecipeDTO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private RecipeDTO(String name, List<String> ingredients, int noOfServings, String instructions) {
		super();
		this.name = name;
		this.ingredients = ingredients;
		this.noOfServings = noOfServings;
		this.instructions = instructions;
	}
	private RecipeDTO() {
		
	}
	public static RecipeDTO of() {
		return new RecipeDTO();
	}
	
	public static RecipeDTO of(String name, List<String> incredients, int noOfServings, String instructions) {
		return new RecipeDTO(name, incredients, noOfServings, instructions);
	}
	
	private String name;
	private List<String> ingredients;
	private int noOfServings;
	private String instructions;
	private String menuCategory;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	public int getNoOfServings() {
		return noOfServings;
	}
	public void setNoOfServings(int noOfServings) {
		this.noOfServings = noOfServings;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public String getMenuCategory() {
		return menuCategory;
	}
	public void setMenuCategory(String menuCategory) {
		this.menuCategory = menuCategory;
	}
	
	
}
