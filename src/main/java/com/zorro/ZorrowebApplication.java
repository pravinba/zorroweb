package com.zorro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class ZorrowebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZorrowebApplication.class, args);
	}
}
