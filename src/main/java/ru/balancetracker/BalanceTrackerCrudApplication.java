package ru.balancetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.balancetracker.security.KeycloakClientCredentials;
import ru.balancetracker.security.KeycloakProperties;

@SpringBootApplication
@EnableJpaAuditing

@EnableConfigurationProperties({KeycloakProperties.class, KeycloakClientCredentials.class})
public class BalanceTrackerCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalanceTrackerCrudApplication.class, args);
    }

}
