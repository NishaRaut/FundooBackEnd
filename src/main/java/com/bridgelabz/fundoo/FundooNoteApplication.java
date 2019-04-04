package com.bridgelabz.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
public class FundooNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNoteApplication.class, args);
	}

}
