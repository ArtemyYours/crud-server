package ru.balanceTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BalanceTrackerCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalanceTrackerCrudApplication.class, args);
    }

}
