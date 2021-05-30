package ru.balancetracker.security.service;

import org.keycloak.KeycloakPrincipal;

import java.security.Principal;

/**
 * interface for creating custom principals in client applications
 */
@FunctionalInterface
public interface PrincipalFactory {

    /**
     * @param principal default KeycloakPrincipal without extensions
     * @return principal implementation of client for further using in application
     */
    Principal create(KeycloakPrincipal principal);
}
