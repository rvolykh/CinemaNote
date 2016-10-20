package com.pikaso.home.cinemanote.app;

import java.security.Principal;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@SpringBootApplication(scanBasePackages = {"com.pikaso.home.cinemanote"})
@RestController
public class Application {
	public static final String[] PUBIC_PLACES = new String[]{"/", "/login", "/information/**",
			"/index.html", "/view/**"};

	@RequestMapping("/login")
	public Principal user(Principal user) {
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.httpBasic()
			.and()
				.authorizeRequests()
					.antMatchers(PUBIC_PLACES).permitAll()
					.anyRequest().authenticated();
		}
	}

}
