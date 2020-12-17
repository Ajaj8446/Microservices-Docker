package com.ajaj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMongoRepositories(basePackages="com.ajaj")
public class MongoDBConfig {
	
	@Bean
	BCryptPasswordEncoder getEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
	