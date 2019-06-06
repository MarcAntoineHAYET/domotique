package com.m2istvequipe3.ServeurDomotique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ServeurDomotiqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServeurDomotiqueApplication.class, args);
	}

}
