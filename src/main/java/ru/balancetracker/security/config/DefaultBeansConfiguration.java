package ru.balancetracker.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.balancetracker.security.service.DefaultPrincipalFactory;
import ru.balancetracker.security.service.PrincipalFactory;

@Configuration
public class DefaultBeansConfiguration {
    @Bean
    public PrincipalFactory defaultPrincipalFactory() {
        return new DefaultPrincipalFactory();
    }
}
