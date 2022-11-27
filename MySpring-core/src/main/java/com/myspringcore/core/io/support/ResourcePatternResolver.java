package com.myspringcore.core.io.support;

import com.myspringcore.core.io.Resource;
import com.myspringcore.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @author julu
 * @date 2022/9/11 14:43
 */
public interface ResourcePatternResolver extends ResourceLoader {

    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";


    Resource[] getResources(String location) throws IOException;
}
