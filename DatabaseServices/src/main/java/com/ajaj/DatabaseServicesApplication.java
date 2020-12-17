package com.ajaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

//As of now disable Spring security
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableEurekaClient
public class DatabaseServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseServicesApplication.class, args);
	}
	
	
	

}
