package com.pollinate.order_management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class OrderManagementServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrderManagementServiceApplication.class, args);
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService(BCryptPasswordEncoder encoder) {
		UserDetails admin = User.withUsername("admin")
				.password(encoder.encode("password"))
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(admin);
	}

}
