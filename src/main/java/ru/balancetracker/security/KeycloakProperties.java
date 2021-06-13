package ru.balancetracker.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    private String authServerUrl;
    private String realm;
    private String resource;

    private KeycloakClientCredentials credentials;


}
