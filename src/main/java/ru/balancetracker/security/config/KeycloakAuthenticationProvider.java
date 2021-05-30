package ru.balancetracker.security.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import ru.balancetracker.security.enchancers.PrincipalEnhancer;
import ru.balancetracker.security.service.BalanceTrackerKeycloakAuthenticationToken;
import ru.balancetracker.security.service.PrincipalFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class KeycloakAuthenticationProvider implements AuthenticationProvider {
    private GrantedAuthoritiesMapper grantedAuthoritiesMapper;
    private final PrincipalFactory principalFactory; //todo: implement
    private final List<PrincipalEnhancer> enhancers;

    public void setGrantedAuthoritiesMapper(GrantedAuthoritiesMapper grantedAuthoritiesMapper) {
        this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        for (String role : token.getAccount().getRoles()) {
            grantedAuthorities.add(new KeycloakRole(role));
        }

        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();


        List<String> projects = (List<String>) principal.getKeycloakSecurityContext().getToken().getOtherClaims().get("projects");
        List<String> media = (List<String>) principal.getKeycloakSecurityContext().getToken().getOtherClaims().get("medias");

        if (projects != null) {
            grantedAuthorities.addAll(projects.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        if (media != null) {
            grantedAuthorities.addAll(media.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        if (enhancers == null || enhancers.isEmpty()) {
            return new BalanceTrackerKeycloakAuthenticationToken(token.getAccount(),
                    token.isInteractive(),
                    mapAuthorities(grantedAuthorities),
                    principalFactory);
        } else {
            return new BalanceTrackerKeycloakAuthenticationToken(
                    token.getAccount(),
                    token.isInteractive(),
                    mapAuthorities(grantedAuthorities),
                    principalFactory,
                    enhancers);
        }
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        return grantedAuthoritiesMapper != null
                ? grantedAuthoritiesMapper.mapAuthorities(authorities)
                : authorities;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return KeycloakAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
