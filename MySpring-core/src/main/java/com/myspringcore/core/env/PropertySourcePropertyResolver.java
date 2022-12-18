package com.myspringcore.core.env;

import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/11/27 15:55
 */
public class PropertySourcePropertyResolver extends AbstractPropertyResolver{

    @Nullable
    private final PropertySources propertySources;

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
    protected String getPropertyAsRawString(String key) {
        return getProperty(key, String.class, false);
    }

    @Nullable
    protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
        if (this.propertySources != null){

        }
        return null;
    }
}
