package com.example.SpringRestAPIWithDataJPASecurityJWTToken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// точка входа в Spring Boot проект
@SpringBootApplication
//Запускает авто-конфигурацию Spring Boot (сканирует пакеты, создаёт бины, настраивает всё автоматически)
public class SpringRestApiWithDataJpaSecurityJwtTokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApiWithDataJpaSecurityJwtTokenApplication.class, args);
    }

//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}

}
