package com.org.recipe;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
public class RecipeManagementApplication {

	private static final String SPRING_APPLICATION_NAME = "spring.application.name";
	@Autowired
	private Environment environment;
	
	public static void main(String[] args) {
		SpringApplication.run(RecipeManagementApplication.class, args);
	}
	
	@PostConstruct
	public void postConstruct() {
		String[] activeProfiles = environment.getActiveProfiles();
		for (String element : activeProfiles) {
			System.out.println(element);
		}
		System.out.println(environment.getProperty(SPRING_APPLICATION_NAME));
		
	
	}

}
