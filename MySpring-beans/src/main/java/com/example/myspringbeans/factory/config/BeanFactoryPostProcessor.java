package com.example.myspringbeans.factory.config;

import com.example.myspringbeans.BeansException;

@FunctionalInterface
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
