package com.myspringcore.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * 本地Spring属性的静态持有器，即定义在Spring库级别。
 *
 * @author julu
 * @date 2022/9/25 17:49
 */
public final class SpringProperties {

    private static final Log logger = LogFactory.getLog(SpringProperties.class);

    private static final Properties localProperties = new Properties();

    public static boolean getFlag(String key){
        return Boolean.parseBoolean(getProperty(key));
    }

    private static String getProperty(String key) {
        String value = localProperties.getProperty(key);
        if (value == null){
            try {
                value = System.getProperty(key);
            }
            catch (Throwable ex) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not retrieve system property '" + key + "': " + ex);
                }
            }
        }
        return value;
    }
}
