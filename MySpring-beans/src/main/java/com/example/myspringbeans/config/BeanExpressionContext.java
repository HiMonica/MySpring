package com.example.myspringbeans.config;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author julu
 * @date 2022/9/13 23:27
 */
public class BeanExpressionContext {

    private final ConfigurableBeanFactory beanFactory;

    @Nullable
    private final Scope scope;

    public BeanExpressionContext(ConfigurableBeanFactory beanFactory, @Nullable Scope scope){
        Assert.notNull(beanFactory, "BeanFactory must not be null");
        this.beanFactory = beanFactory;
        this.scope = scope;
    }


}
