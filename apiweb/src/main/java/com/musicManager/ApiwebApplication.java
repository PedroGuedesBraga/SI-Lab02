package com.musicManager;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.musicManager.repository")
public class ApiwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiwebApplication.class, args);
	}
}
