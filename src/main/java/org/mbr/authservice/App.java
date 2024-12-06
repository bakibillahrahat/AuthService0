package org.mbr.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"org.mbr.authservice.repository"})
//@ComponentScan(basePackages = {"org.mbr.authservice.controller", "org.mbr.authservice.service", "org.mbr.authservice.auth" })
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
