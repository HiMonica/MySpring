package com.example.myspringbeans.xml;

import com.myspringcore.core.io.ResourceLoader;

/**
 * @author julu
 * @date 2022/11/20 19:44
 */
public class ResourceEntityResolver extends DelegatingEntityResolver{

    private final ResourceLoader resourceLoader;

    public ResourceEntityResolver(ResourceLoader resourceLoader){
        super(resourceLoader.getClassLoader());
        this.resourceLoader = resourceLoader;
    }
}
