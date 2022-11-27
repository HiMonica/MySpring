package com.example.myspringbeans.support;

import com.myspringcore.core.io.Resource;
import com.myspringcore.core.io.ResourceLoader;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.lang.Nullable;

/**
 * Bean定义的阅读器
 *
 * 抽象类
 * @see com.example.myspringbeans.support.AbstractBeanDefinitionReader
 *
 * 具体实现
 * 1、xml文件形成的解析方式
 * @see com.example.myspringbeans.xml.XmlBeanDefinitionReader
 *
 * @author julu
 * @date 2022/11/20 17:38
 */
public interface BeanDefinitionReader {

    /**
     * 返回bean定义的工厂
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 返回用于资源位置的资源加载器
     */
    @Nullable
    ResourceLoader getResourceLoader();

    /**
     * 返回使用的类加载器
     */
    @Nullable
    ClassLoader getBeanClassLoader();

    /**
     * 从指定的资源加载bean定义
     */
    int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException;

    /**
     * 从指定的资源加载bean定义
     */
    int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException;

    /**
     * 从指定的资源路径加载beanDefinition
     */
    int loadBeanDefinitions(String location) throws BeanDefinitionStoreException;

    /**
     * 从指定的资源路径加载beanDefinition
     */
    int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException;
}
