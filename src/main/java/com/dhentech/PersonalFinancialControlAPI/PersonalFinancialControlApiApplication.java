package com.dhentech.PersonalFinancialControlAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PersonalFinancialControlApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalFinancialControlApiApplication.class, args);
	}

}
