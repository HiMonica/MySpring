package com.example.myspringbeans.config;

import com.example.myspringbeans.factory.BeanFactory;
import com.example.myspringbeans.factory.HierarchicalBeanFactory;
import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/9/11 09:51
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {

    /**
     * 模式状态
     */
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 销毁工厂中的所有单例
     */
    void destroySingletons();

    /**
     * 设置此bean工厂的父组件
     * @param parentBeanFactory
     * @throws IllegalStateException
     */
    void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException;

    /**
     * 设置此bean工厂的类加载器
     * @param beanClassLoader
     */
    void setBeanClassLoader(@Nullable ClassLoader beanClassLoader);

    /**
     * 获取此bean工厂的类加载器
     * @return
     */
    @Nullable
    ClassLoader getBeanClassLoader();

    /**
     * 指定用于类型匹配目的的临时ClassLoader
     * @param tempClassLoader
     */
    void setTempClassLoader(@Nullable ClassLoader tempClassLoader);

    /**
     * 获取临时ClassLoader
     * @return
     */
    @Nullable
    ClassLoader getTempClassLoader();

    /**
     * 设置metaData参数
     * @param cacheBeanMetaData
     */
    void setCacheBeanMetaData(boolean cacheBeanMetaData);

    /**
     * 是否缓存metadata
     * @return
     */
    boolean isCacheBeanMetaData();


}
