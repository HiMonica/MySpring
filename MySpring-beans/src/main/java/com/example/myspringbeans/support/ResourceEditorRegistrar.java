package com.example.myspringbeans.support;

import com.example.myspringbeans.PropertyEditorRegistrar;
import com.myspringcore.core.env.PropertyResolver;
import com.myspringcore.core.io.ResourceLoader;

/**
 * 用于bean在创建一个ApplicationContext资源编辑器。使用在AbstractApplicationContext
 *
 * @author julu
 * @date 2022/9/18 10:46
 */
public class ResourceEditorRegistrar implements PropertyEditorRegistrar {

    private final PropertyResolver propertyResolver;

    private final ResourceLoader resourceLoader;

    public ResourceEditorRegistrar(ResourceLoader resourceLoader, PropertyResolver propertyResolver){
        this.resourceLoader = resourceLoader;
        this.propertyResolver = propertyResolver;
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistrar register) {

    }
}
