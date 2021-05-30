package ru.balancetracker.security.service;

import lombok.Getter;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.springframework.security.core.GrantedAuthority;
import ru.balancetracker.security.enchancers.PrincipalEnhancer;
import ru.balancetracker.security.model.BalanceTrackerPrincipal;

import java.security.Principal;
import java.util.Collection;
import java.util.List;


/**
 * Workaround for Keycloak default implementation.
 * Creating custom private  principal to override KeycloakAuthenticationToken own principal
 * and extend it for convenient for client model
 */
@Getter
public class BalanceTrackerKeycloakAuthenticationToken extends org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken {

    private Principal principal;

    public BalanceTrackerKeycloakAuthenticationToken(KeycloakAccount account, boolean interactive, PrincipalFactory principalFactory) {
        super(account, interactive);
        this.principal = principalFactory.create((KeycloakPrincipal) account.getPrincipal()); //todo: implement
    }

    public BalanceTrackerKeycloakAuthenticationToken(KeycloakAccount account,
                                                     boolean interactive,
                                                     PrincipalFactory principalFactory,
                                                     List<PrincipalEnhancer> enhancers) {
        super(account, interactive);
        this.principal = principalFactory.create((KeycloakPrincipal) account.getPrincipal()); //todo: implement
        for (PrincipalEnhancer enhancer : enhancers) {
            enhancer.enhance((BalanceTrackerPrincipal) principal);
        }
    }

    public BalanceTrackerKeycloakAuthenticationToken(KeycloakAccount account,
                                                     boolean interactive,
                                                     Collection<? extends GrantedAuthority> authorities,
                                                     PrincipalFactory principalFactory) {
        super(account, interactive, authorities);
        this.principal = principalFactory.create((KeycloakPrincipal) account.getPrincipal()); //todo: implement

    }

    public BalanceTrackerKeycloakAuthenticationToken(KeycloakAccount account,
                                                     boolean interactive,
                                                     Collection<? extends GrantedAuthority> authorities,
                                                     PrincipalFactory principalFactory,
                                                     List<PrincipalEnhancer> enhancers) {
        super(account, interactive, authorities);
        this.principal = principalFactory.create((KeycloakPrincipal) account.getPrincipal()); //todo: implement
        for (PrincipalEnhancer enhancer : enhancers) {
            enhancer.enhance((BalanceTrackerPrincipal) principal);
        }
    }

}
