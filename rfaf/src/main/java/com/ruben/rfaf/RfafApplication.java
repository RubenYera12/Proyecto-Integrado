package com.ruben.rfaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RfafApplication {

	public static void main(String[] args) {
		SpringApplication.run(RfafApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			System.out.println(
//					userService.addUser(
//							new User(
//									"USER0001",
//									"Ruben01",
//									"ruben123",
//									"Ruben",
//									"Yera",
//									"ruben@bosonit.es",
//									"Jaén",
//									new Date(),
//									"",
//									true,
//									"División de Honor")));
//		};
//	}

}
