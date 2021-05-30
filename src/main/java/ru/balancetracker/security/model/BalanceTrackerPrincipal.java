package ru.balancetracker.security.model;

import lombok.Getter;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

@Getter
public class BalanceTrackerPrincipal extends KeycloakPrincipal<KeycloakSecurityContext>{
    private String id;
    private String preferredUsername;
    private String email;
    private String firstName;
    private String lastName;
    private boolean emailVerified;

    public BalanceTrackerPrincipal(KeycloakPrincipal<KeycloakSecurityContext> principal) {
        super(principal.getName(), principal.getKeycloakSecurityContext());
        AccessToken keycloakToken = principal.getKeycloakSecurityContext().getToken();
        this.id = keycloakToken.getSubject();
        this.preferredUsername = keycloakToken.getPreferredUsername();

        this.firstName =keycloakToken.getGivenName();
        this.lastName = keycloakToken.getFamilyName();
        this.email = keycloakToken.getEmail();
    }
}
