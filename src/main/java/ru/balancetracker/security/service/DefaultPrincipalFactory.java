package ru.balancetracker.security.service;

import org.keycloak.KeycloakPrincipal;
import ru.balancetracker.security.model.BalanceTrackerPrincipal;

import java.security.Principal;

public class DefaultPrincipalFactory implements PrincipalFactory {
    @Override
    public Principal create(KeycloakPrincipal principal) {
        return new BalanceTrackerPrincipal(principal);
    }
}