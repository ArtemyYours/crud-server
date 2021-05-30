package ru.balancetracker.security.utils;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.KeycloakAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.balancetracker.security.model.BalanceTrackerPrincipal;

public class SecurityUtils {
    public static BalanceTrackerPrincipal getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            return new BalanceTrackerPrincipal((KeycloakPrincipal<KeycloakSecurityContext>) principal);
        }
        throw new KeycloakAuthenticationException("Security context has no keycloak principal. User is not identified");
    }

    public static String getToken(){
        KeycloakPrincipal<KeycloakSecurityContext> principal =  (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getKeycloakSecurityContext().getTokenString();
    }
}
