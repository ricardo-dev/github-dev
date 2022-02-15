package com.ricardo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ricardo.api.config.property.DevGitApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(DevGitApiProperty.class)
public class DevGitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevGitApplication.class, args);
	}
	
}
