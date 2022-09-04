package com.example.myspringbeans.factory;

import org.springframework.lang.Nullable;

/**
 * 我的理解：这个接口是控制BeanFactory的
 */
public interface HierarchicalBeanFactory extends BeanFactory{

    /**
     * 返回父bean工厂，或者为空就返回null
     */
    @Nullable
    BeanFactory getParentBeanFactory();

    /**
     * 通过bean工厂的名称判断是否包含该bean工厂
     */
    boolean containsLocalBean(String name);
}
