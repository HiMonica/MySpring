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

    /**
     * 返回在给定名称下注册的(原始)单例对象
     *
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     *  检查此注册表是否包含具有给定名称的单例实例。
     *
     * @param beanName
     * @return
     */
    boolean containSingleton(String beanName);

    /**
     * 返回在此注册中心注册的单例bean的名称
     *
     * @return
     */
    String[] getSingletonNames();

    /**
     * 返回该注册中心单例bean数量
     *
     * @return
     */
    int getSingletonCount();

    /**
     *  返回此注册表使用的单例互斥对象(用于外部合作者)
     *
     * @return
     */
    Object getSingletonMutex();
}
