package com.org.recipe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
	private static final String SPRING_DATA_MONGODB_DATABASE_PROP_NAME = "spring.data.mongodb.database";
	private static final String SPRING_DATA_MONGODB_URL_PROP_NAME = "spring.data.mongodb.uri";
	@Autowired
	private Environment environment;
//private static final String MONGO_DB_NAME = "mongoDB";
//private static final String MONGODB_LOCALHOST_27017_MONGO_DB = "mongodb://localhost:27017/mongoDB";
	 
	
	@Bean
	public MongoClient mongo() {
		String dbURL = environment.getProperty(SPRING_DATA_MONGODB_URL_PROP_NAME);
		ConnectionString connectionString=new ConnectionString(dbURL);
		MongoClientSettings mongoClientSettings=MongoClientSettings.builder().applyConnectionString(connectionString).build();
		return MongoClients.create(mongoClientSettings);
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		String dbName = environment.getProperty(SPRING_DATA_MONGODB_DATABASE_PROP_NAME);
		return new MongoTemplate(mongo(),dbName);
	}
}
