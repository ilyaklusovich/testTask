package com.test.postservice.letterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableJpaRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class LetterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LetterServiceApplication.class, args);
    }
}
