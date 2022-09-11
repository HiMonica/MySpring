package org.springframework.core.io.support;

import org.springframework.core.io.ResourceLoader;

/**
 * @author julu
 * @date 2022/9/11 14:43
 */
public interface ResourcePatternResolver extends ResourceLoader {

    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";


}
