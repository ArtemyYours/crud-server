package ru.balanceTracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Student-to-Teacher REST API",
                "This API consist of three controllers. Student controller (CRUD operations + get al Teachers, who work with this student), " +
                        "Teacher controller((CRUD operations + get al Students, who work with this teacher)), " +
                        "Student-to-teacher-relation-controller(add aor delete relation student-teacher)\n ",
                "0.99",
                "Terms of service",
                new Contact("Artem Kopilov", "https://github.com/ArtemyYours", "artemykopilov@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}
