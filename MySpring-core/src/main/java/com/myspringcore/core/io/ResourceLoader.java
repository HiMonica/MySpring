package com.myspringcore.core.io;

import com.myspringcore.util.ResourceUtils;
import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/9/11 14:43
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;

    Resource getResource(String location);

    @Nullable
    ClassLoader getClassLoader();
}
