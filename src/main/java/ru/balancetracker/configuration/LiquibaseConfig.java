package ru.balancetracker.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "balance-tracker")
public class LiquibaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "balance-tracker.pre-jpa-liquibase")
    public LiquibaseProperties preJpaLiquibaseProp() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "balance-tracker.post-jpa-liquibase")
    public LiquibaseProperties postJpaLiquibaseProp() {
        return new LiquibaseProperties();
    }

    @Bean(name = "preJpaLiquibase")
    @Autowired
    public SpringLiquibase preJpaLiquibase(DataSource dataSourceApp,
                                           @Qualifier(value = "preJpaLiquibaseProp") LiquibaseProperties properties) {
        return createSpringLiquibaseFromProperties(dataSourceApp, properties);
    }

    @Bean(name = "postJpaLiquibase")
    @Autowired
    @DependsOn("entityManagerFactory")
    public SpringLiquibase postJpaLiquibase(DataSource dataSourceApp,
                                            @Qualifier(value = "postJpaLiquibaseProp") LiquibaseProperties properties) {
        return createSpringLiquibaseFromProperties(dataSourceApp, properties);
    }

    private SpringLiquibase createSpringLiquibaseFromProperties(DataSource dataSourceApp,
                                                                @Qualifier("postJpaLiquibaseProp") LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setContexts(properties.getContexts());
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setDataSource(dataSourceApp);
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLiquibaseSchema(properties.getLiquibaseSchema());
        liquibase.setDatabaseChangeLogTable(properties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(properties.getDatabaseChangeLogLockTable());
        return liquibase;
    }


}
