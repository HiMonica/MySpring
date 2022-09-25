package com.example.myspringbeans.config;

/**
 * 为共享bean定义注册中心
 *
 * @author julu
 * @date 2022/9/25 10:18
 */
public interface SingletonBeanRegistry {

    /**
     * 将给定的现有对象注册为bean注册表中的单例对象，根据beanName
     *
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);
}
