package com.spring.app.util;

import com.spring.app.config.PropertiesConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertiesConfigUtil {
    private final PropertiesConfig propertiesConfig;

    public String getMessage(final String value) {
        return propertiesConfig.getProperty(value);
    }

}