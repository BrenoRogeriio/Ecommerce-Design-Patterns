package com.ecommercedesignpatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.model")
@EnableJpaRepositories(basePackages = "com.repository")
@ComponentScan(basePackages = {
        "com.controller",
        "com.ecommercedesignpatterns",
        "com.repository",
        "com.exception"
})
public class EcommerceDesignPatternsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceDesignPatternsApplication.class, args);
    }
}