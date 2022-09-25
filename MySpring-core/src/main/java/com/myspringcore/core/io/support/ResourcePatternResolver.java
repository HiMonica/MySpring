package com.myspringcore.core.io.support;

import com.myspringcore.core.io.ResourceLoader;

/**
 * @author julu
 * @date 2022/9/11 14:43
 */
public interface ResourcePatternResolver extends ResourceLoader {

    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";


}
