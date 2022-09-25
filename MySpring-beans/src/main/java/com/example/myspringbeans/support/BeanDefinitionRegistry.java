package com.example.myspringbeans.support;

import com.example.myspringbeans.factory.config.BeanDefinition;
import com.myspringcore.core.AliasRegistry;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;


/**
 * 用于保存
 *
 * @author julu
 * @date 2022/9/19 23:39
 */
public interface BeanDefinitionRegistry extends AliasRegistry {

    /**
     * 在此注册中心注册一个bean定义
     * @param beanName
     * @param beanDefinition
     * @throws BeanDefinitionStoreException
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
        throws BeanDefinitionStoreException;

    /**
     * 移除给定名称的BeanDefinition
     * @param beanName
     * @throws NoSuchBeanDefinitionException
     */
    void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * 获取指定名称的beanDefinition
     * @param beanName
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * 检查此注册中心是否包含了给定名称的bean定义
     * @param beanName
     * @return
     */
    Boolean containsBeanDefinition(String beanName);

    /**
     * 返回此注册表中所有定义的bean名称
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 返回此注册表中所有定义的bean总数
     * @return
     */
    int getBeanDefinitionCont();

    /**
     * 确认给定的bean名称是否在使用
     * @param beanName
     * @return
     */
    boolean isBeanNameInUse(String beanName);
}
