package com.myspringcore.core.env;

import org.springframework.core.env.MissingRequiredPropertiesException;

import java.util.Map;

/**
 * @author julu
 * @date 2022/9/25 10:38
 */
public class StandardEnvironment implements ConfigurableEnvironment{
    @Override
    public Map<String, Object> getSystemProperties() {
        return null;
    }

    @Override
    public Map<String, Object> getSystemEnvironment() {
        return null;
    }

    @Override
    public void validateRequiredProperties() throws MissingRequiredPropertiesException {

    }

    @Override
    public boolean containsProperty(String key) {
        return false;
    }
}
