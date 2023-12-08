package com.org.recipe.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.org.recipe.RecipeManagementApplication;

@SpringBootTest(classes = RecipeManagementApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AbstractTest extends AbstractTestNGSpringContextTests {

}