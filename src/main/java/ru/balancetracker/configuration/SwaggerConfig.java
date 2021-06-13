package ru.balancetracker.configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.balancetracker.security.KeycloakProperties;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@Slf4j
@EnableSwagger2
@AllArgsConstructor
public class SwaggerConfig {

    private KeycloakProperties properties;

    @Bean
    public SecurityConfiguration securityConfiguration() {

        Map<String, Object> additionalQueryStringParams = new HashMap<>();

        return SecurityConfigurationBuilder.builder()
                .clientId(properties.getResource())
                .realm(properties.getRealm())
                .appName(properties.getResource())
                .additionalQueryStringParams(additionalQueryStringParams)
                .build();
    }

    @Bean
    public Docket api() {

        ParameterBuilder apiParameterBuilder = new ParameterBuilder();
        Parameter apiParameter = apiParameterBuilder.name("realm")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue(properties.getRealm())
                .required(true)
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes()
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(Collections.singletonList(apiParameter))
                .securitySchemes(buildSecurityScheme())
                .securityContexts(buildSecurityContext())
                .pathMapping("/")
                .useDefaultResponseMessages(true)
                .apiInfo(new ApiInfoBuilder().build());
    }


    private List<SecurityContext> buildSecurityContext() {
        List<SecurityReference> securityReferences = new ArrayList<>();

        securityReferences.add(
                SecurityReference
                        .builder()
                        .reference("oauth2")
                        .scopes(scopes()
                                .toArray(new AuthorizationScope[]{})).build()
        );

        SecurityContext context = SecurityContext.builder().forPaths(s -> true).securityReferences(securityReferences).build();

        List<SecurityContext> ret = new ArrayList<>();
        ret.add(context);
        return ret;
    }

    private List<? extends SecurityScheme> buildSecurityScheme() {
        List<SecurityScheme> lst = new ArrayList<>();

        String keycloakUrl = properties.getAuthServerUrl();
        String defaultRealm = properties.getRealm();
        LoginEndpoint login = new LoginEndpointBuilder()
                .url(keycloakUrl + "realms/" + defaultRealm + "/protocol/openid-connect/auth")
                .build();

        List<GrantType> gTypes = new ArrayList<>();
        gTypes.add(new ImplicitGrant(login, "access_token"));

        lst.add(new OAuth("oauth2", scopes(), gTypes));
        return lst;
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        for (String scopeItem : new String[]{"openid=openid", "profile=profile"}) {
            String[] scope = scopeItem.split("=");
            if (scope.length == 2) {
                scopes.add(new AuthorizationScopeBuilder().scope(scope[0]).description(scope[1]).build());
            } else {
                log.warn("Scope '{}' is not valid (format is scope=description)", scopeItem);
            }
        }

        return scopes;
    }
}
