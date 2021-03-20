package com.spring.app.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:app_messages.properties")
@RequiredArgsConstructor
public class PropertiesConfig {

    private final Environment environment;

    public String getProperty(String propertyKey) {

        if (StringUtils.isBlank(propertyKey)) {
            log.error("PropertiesConfig.getProperty() :: propertyKey is NULL");

            return null;
        }

        String propertyValue = StringUtils.EMPTY;

        try {
            propertyValue = environment.getProperty(propertyKey);

        } catch (Exception exception) {
            log.error("PropertiesConfig.getProperty() :: Unable to find value for the key = '{}'", propertyKey);
        }

        return propertyValue;
    }

}