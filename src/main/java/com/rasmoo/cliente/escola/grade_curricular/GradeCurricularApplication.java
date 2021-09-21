package com.rasmoo.cliente.escola.grade_curricular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GradeCurricularApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradeCurricularApplication.class, args);
	}

}
