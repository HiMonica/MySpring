package com.myspringcore.core.env;

import org.springframework.core.env.MissingRequiredPropertiesException;

/**
 * @author julu
 * @date 2022/11/27 15:55
 */
public class PropertySourcePropertyResolver extends AbstractPropertyResolver{

    private PropertySources propertySources;

    public PropertySourcePropertyResolver(PropertySources propertySources){
        this.propertySources = propertySources;
    }

    @Override
    public void validateRequiredProperties() throws MissingRequiredPropertiesException {

    }

    @Override
    public boolean containsProperty(String key) {
        return false;
    }

    @Override
    public String resolveRequirePlaceholders(String text) throws IllegalArgumentException {
        return null;
    }
}
