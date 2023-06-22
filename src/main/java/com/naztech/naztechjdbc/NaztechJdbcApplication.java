package com.naztech.naztechjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class NaztechJdbcApplication {

	public static void main(String[] args) {
		
	SpringApplication.run(NaztechJdbcApplication.class, args);
	 
	 
	}
	
	@GetMapping("/")
	public String getWelcomeMsg() {
		return "Hello World";
	}

}
