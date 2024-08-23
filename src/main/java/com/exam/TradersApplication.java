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
			        .allowedOrigins(
			                "http://localhost:3000",
			                "http://traders5reactbucket.s3-website-ap-northeast-1.amazonaws.com",
			                "*" 
			            );

			}
		};
	}

}
