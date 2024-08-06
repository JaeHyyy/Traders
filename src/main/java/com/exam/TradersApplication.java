package com.exam;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling     //자정에 유통기한 지난 상품 자동으로 disuse테이블에 저장 시키는 어노테이션
public class TradersApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TradersApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TradersApplication.class);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		System.out.println("WebMvcConfigurer.addCorsMappings");
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("*")

					.allowedOrigins("http://localhost:3000/**",
							"http://127.0.0.1:5500",
							"http://10.10.10.207:8090/**",
							"http://10.10.10.197:8090/**",
							"http://192.168.0.109:8090/**",
							"http://10.10.10.170:8090/**",
							"*");

					

			}
		};
	}

}
