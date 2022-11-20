package com.myspringcore.core.io.support;

import com.myspringcore.core.io.DefaultResourceLoader;
import com.myspringcore.core.io.Resource;
import com.myspringcore.core.io.ResourceLoader;
import org.springframework.util.Assert;

/**
 * @author julu
 * @date 2022/9/11 14:55
 */
public class PathMatchingResourcePatternResolver implements ResourcePatternResolver{

    private final ResourceLoader resourceLoader;

    public PathMatchingResourcePatternResolver(){
        this.resourceLoader = new DefaultResourceLoader();
    }

    public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader){
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Resource getResource(String location) {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}
