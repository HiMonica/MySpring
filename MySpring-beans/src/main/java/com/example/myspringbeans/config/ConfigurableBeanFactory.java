package com.example.myspringbeans.config;

import com.example.myspringbeans.factory.HierarchicalBeanFactory;

/**
 * @author julu
 * @date 2022/9/11 09:51
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {

    /**
     * 销毁工厂中的所有单例
     */
    void destroySingletons();
}
