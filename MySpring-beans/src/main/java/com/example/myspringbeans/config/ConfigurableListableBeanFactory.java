package com.example.myspringbeans.config;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.factory.ListableBeanFactory;
import org.springframework.lang.Nullable;

/**
 * @author julu
 * 大多数工厂实现的配置接口
 * @date 2022/9/11 09:28
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, ConfigurableBeanFactory {

    /**
     * 忽略自动装配的给定依赖类型
     *
     * @param type
     */
    void ignoreDependencyType(Class<?> type);

    /**
     * 忽略自动装配的给定依赖项接口
     *
     * @param ifc
     */
    void ignoreDependencyInterface(Class<?> ifc);

    /**
     *  注册具有相应自动连接值的特殊依赖类型
     *
     * @param dependencyType
     * @param autowiredValue
     */
    void registerResolvableDependency(Class<?> dependencyType, @Nullable Object autowiredValue);

//    boolean isAutowireCandidate(String beanName, )

    /**
     * 返回该工厂管理的所有bean名称的统一视图
     *
     * @return
     */
    Iterable<String> getBeanNameIterator();

    /**
     * 清除合并的bean定义缓存
     */
    void clearMetadataCache();

    /**
     * 冻结所有的bean定义，通知已注册的bean定义
     */
    void freezeConfiguration();

    /**
     * 返回该工厂bean定义是否被冻结
     *
     * @return
     */
    boolean isConfigurationFrozen();

    /**
     * 确保所有非惰性初始化的单例都已实例化
     *
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;
}
