package com.kurtlobato.challenge.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder jsonBuilderConfig = new Jackson2ObjectMapperBuilder();
        jsonBuilderConfig.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return jsonBuilderConfig;
    }

}
