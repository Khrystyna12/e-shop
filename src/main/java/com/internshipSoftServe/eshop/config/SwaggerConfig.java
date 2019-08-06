package com.internshipSoftServe.eshop.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.internshipSoftServe.eshop.controller.ArticlesController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@ComponentScan(basePackageClasses = ArticlesController.class)
@Configuration
public class SwaggerConfig {
    private static final String SWAGGER_API_VERSION = "1,0";
    private static final String LICENSE_TEXT = "Licensed by Internship2019";
    private static final String TITLE = "E-Shop rest api";
    private static final String DESCRIPTION = "RestFULL API for products, categories and articles";

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .license(LICENSE_TEXT)
                .version(SWAGGER_API_VERSION)
                .build();
    }

    Map<String, Object> container = new HashMap<>();

    {
        container.put("Docket", new Docket(DocumentationType.SWAGGER_2));

        Object docket = container.get("Docket");
    }

    Object docket = container.get("Docket");

    @Bean
    public Docket productsApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("localhost:8090"))
                .paths(regex("/shop.*"))
                .build()
                .apiInfo(apiInfo());
    }
}
