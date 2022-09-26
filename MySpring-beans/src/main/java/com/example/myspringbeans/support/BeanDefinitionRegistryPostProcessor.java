package com.example.myspringbeans.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.factory.config.BeanFactoryPostProcessor;

/**
 * 扩展到标准的BeanFactoryPostProcessor的SPI
 * 允许正则之前注册的进一步bean定义BeanFactoryPostProcessor检测启动。
 * 特别是, BeanDefinitionRegistryPostProcessor可以注册更多的bean定义，
 * 它们又定义BeanFactoryPostProcessor实例。
 *
 * @author julu
 * @date 2022/9/26 23:11
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

    /**
     * 修改应用程序上下文的内部bean定义注册表标准的初始化
     *
     * @param registry
     * @throws BeansException
     */
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;
}
