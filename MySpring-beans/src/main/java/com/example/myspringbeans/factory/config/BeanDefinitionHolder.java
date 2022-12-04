package com.example.myspringbeans.factory.config;

import com.example.myspringbeans.BeanMetadataElement;
import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/12/4 16:08
 */
public class BeanDefinitionHolder implements BeanMetadataElement {

    private final BeanDefinition beanDefinition;

    private final String beanName;

    @Nullable
    private final String[] aliases;

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName){
        this(beanDefinition, beanName, null);
    }

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, String[] aliases){
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
        this.aliases = aliases;
    }

    @Override
    public Object getSource() {
        return null;
    }
}
