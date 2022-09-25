package com.example.myspringbeans.context;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.context.weaving.ApplicationContext;
import com.myspringcore.core.env.ConfigurableEnvironment;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;


import org.springframework.lang.Nullable;

/**
 * 这里封装了配置和生命周期方法，以避免使用
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    String CONFIG_LOCATION_DELIMITERS = ",; \t\n";

    String CONVERSION_SERVICE_BEAN_NAME = "conversionService";

    String LOAD_TIME_WEAVER_BEAN_NAME = "loadTimeWeaver";

    String ENVIRONMENT_BEAN_NAME = "environment";

    String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";

    String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";

    /**
     * 设置此应用程序上下文唯一的id
     */
    void setId(String id);

    /**
     * 设置应用程序上下文父级
     */
    void setParent(@Nullable ApplicationContext parent);

    /**
     * 设置应用程序上下文Environment
     */
    void setEnvironment(ConfigurableApplicationContext environment);

    /**
     * 获取程序上下文Environment
     */
    @Override
    ConfigurableEnvironment getEnvironment();

    /**
     * 添加一个新的 BeanFactoryPostProcessor，它将在刷新时应用于此应用程序上下文的内部 bean 工厂，然后再计算任何 bean 定义。在上下文配置期间调用
     * @param beanFactory
     * @throws BeansException
     */
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactory) throws BeansException;

    /**
     * 添加一个新的ApplicationListener，它将在上下文事件时收到通知
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    // TODO: 2022/8/28 后期添加

    /**
     * load类，spring容器加载最主要的类
     * 加载或刷新配置的持久表示，
     * 可能是XML文件、属性文件或关系数据库模式。
     * 因为这是一个启动方法，它应该销毁已经创建的单例
     * @throws BeansException
     * @throws IllegalStateException
     */
    void refresh() throws BeansException, IllegalStateException;

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
