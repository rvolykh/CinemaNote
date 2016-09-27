package com.pikaso.home.cinemanote.app;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application startup
 * @author pikaso
 */
@EnableJSONDoc
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan({"com.pikaso.home.cinemanote.entity"})
@EnableJpaRepositories({"com.pikaso.home.cinemanote"})
@SpringBootApplication(scanBasePackages = { "com.pikaso.home.cinemanote"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
