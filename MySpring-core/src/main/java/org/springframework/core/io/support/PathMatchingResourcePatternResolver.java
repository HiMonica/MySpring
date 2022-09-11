package org.springframework.core.io.support;

import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

/**
 * @author julu
 * @date 2022/9/11 14:55
 */
public class PathMatchingResourcePatternResolver implements ResourcePatternResolver{

    private final ResourceLoader resourceLoader;

    public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader){
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");
        this.resourceLoader = resourceLoader;
    }
}
