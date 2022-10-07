package com.example.myspringbeans.config;

import com.example.myspringbeans.BeanMetadataElement;
import com.example.myspringbeans.factory.config.BeanDefinition;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author julu
 * @date 2022/10/7 10:11
 */
public class BeanDefinitionHolder implements BeanMetadataElement {

    private final BeanDefinition beanDefinition;

    private final String beanName;

    @Nullable
    private final String[] aliases;

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
        this(beanDefinition, beanName, null);
    }

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, @Nullable String[] aliases) {
        Assert.notNull(beanDefinition, "BeanDefinition must not be null");
        Assert.notNull(beanName, "Bean name must not be null");
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
        this.aliases = aliases;
    }

    @Override
    public Object getSource() {
        return this.beanDefinition.getSource();
    }
}
