package com.myspringcore.core.env;

import com.myspringcore.core.SpringProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.lang.Nullable;

import java.security.AccessControlException;
import java.util.Collections;
import java.util.Map;

/**
 * @author julu
 * @date 2022/9/25 17:23
 */
public abstract class AbstractEnvironment implements ConfigurableEnvironment{

    protected final Log logger = LogFactory.getLog(getClass());

    public static final String IGNORE_GETENV_PROPERTY_NAME = "spring.getenv.ignore";

    // TODO: 2022/11/27 具体实现类
    private PropertySources propertySources;

    private final ConfigurablePropertyResolver propertyResolver =
            new PropertySourcePropertyResolver(this.propertySources);

    @Override
    public Map<String, Object> getSystemProperties() {
        try {
            return (Map) System.getProperties();
        }
        catch (AccessControlException ex){
            return (Map) new ReadOnlySystemAttributesMap(){

                @Override
                @Nullable
                protected String getSystemAttribute(String attributeName) {
                    try {
                        return System.getenv(attributeName);
                    }
                    catch (AccessControlException ex) {
                        if (logger.isInfoEnabled()) {
                            logger.info("Caught AccessControlException when accessing system environment variable '" +
                                    attributeName + "'; its value will be returned [null]. Reason: " + ex.getMessage());
                        }
                        return null;
                    }
                }
            };

        }
    }

    @Override
    public Map<String, Object> getSystemEnvironment() {
        if (suppressGetenvAccess()){
            return Collections.emptyMap();
        }
        try {
            return (Map) System.getenv();
        }
        catch (AccessControlException ex){
            return (Map) new ReadOnlySystemAttributesMap(){

                @Override
                protected String getSystemAttribute(String attributeName) {
                    try {
                        return System.getenv(attributeName);
                    } catch (AccessControlException ex) {
                        if (logger.isInfoEnabled()) {
                            logger.info("Caught AccessControlException when accessing system environment variable '" +
                                    attributeName + "'; its value will be returned [null]. Reason: " + ex.getMessage());
                        }
                        return null;
                    }
                }
            };
        }
    }

    protected boolean suppressGetenvAccess(){
        return SpringProperties.getFlag(IGNORE_GETENV_PROPERTY_NAME);
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
        return this.propertyResolver.resolveRequirePlaceholders(text);
    }
}
