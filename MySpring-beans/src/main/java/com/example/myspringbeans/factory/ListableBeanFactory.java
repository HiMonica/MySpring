package com.example.myspringbeans.factory;

import com.example.myspringbeans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 我的理解：这个接口是控制BeanDefinition的
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 是否存在给定名字的beanDefinition
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回工厂的BeanDefinition总数
     */
    int getBeanDefinitionCount();

    /**
     * 返回工厂中所有bean的名称
     */
    String[] getBeanDefinitionNames();

    /**
     * 根据bean的类型获取bean
     */
    String[] getBeanNamesForType(ResolvableType type);

    /**
     * 返回指定类型bean的所有名字
     */
    String[] getBeanNamesForType(@Nullable Class<?> type);

    /**
     * 返回指定类型的名字 includeNonSingletons为false表示只取单例Bean，true则不是
     * allowEagerInit为true表示立刻加载，false表示延迟加载。 注意：FactoryBeans都是立刻加载的。
     */
    String[] getBeanNamesForType(@Nullable Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);

    /**
     * 根据类型（包括子类）返回指定Bean名和Bean的Map
     */
    <T> Map<String,T> getBeansOfType(@Nullable Class<T> type) throws BeansException;

    <T> Map<String,T> getBeansOfType(@Nullable Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException;

    /**
     * 查询使用注解的类
     */
    String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);

    /**
     * 根据注解类型查找所有这个注解的bean名称和Map
     */
    Map<String,Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException;

    /**
     * 根据bean名和注解类型查找指定的bean
     */
    @Nullable
    <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException;


}
