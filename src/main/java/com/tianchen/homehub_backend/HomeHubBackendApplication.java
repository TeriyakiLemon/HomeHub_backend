package com.tianchen.homehub_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HomeHubBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeHubBackendApplication.class, args);
	}

}
