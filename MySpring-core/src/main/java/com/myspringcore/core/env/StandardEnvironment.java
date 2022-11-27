package com.myspringcore.core.env;

import org.springframework.core.env.MissingRequiredPropertiesException;

import java.util.Map;

/**
 * @author julu
 * @date 2022/9/25 10:38
 */
public class StandardEnvironment extends AbstractEnvironment{

    @Override
    public String resolveRequirePlaceholders(String text) throws IllegalArgumentException {
        return null;
    }
}
