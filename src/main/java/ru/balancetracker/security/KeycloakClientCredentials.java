package ru.balancetracker.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak.credentials")
public class KeycloakClientCredentials {
    private String secret;
}
