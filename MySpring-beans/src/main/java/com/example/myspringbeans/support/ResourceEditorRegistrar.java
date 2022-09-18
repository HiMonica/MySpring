package com.example.myspringbeans.support;

import com.example.myspringbeans.PropertyEditorRegister;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.ResourceLoader;

/**
 * @author julu
 * @date 2022/9/18 10:46
 */
public class ResourceEditorRegistrar implements PropertyEditorRegister {

    private final PropertyResolver propertyResolver;

    private final ResourceLoader resourceLoader;

    public ResourceEditorRegistrar(ResourceLoader resourceLoader, PropertyResolver propertyResolver){
        this.resourceLoader = resourceLoader;
        this.propertyResolver = propertyResolver;
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegister register) {

    }
}
