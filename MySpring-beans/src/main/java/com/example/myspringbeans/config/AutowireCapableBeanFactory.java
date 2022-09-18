package com.example.myspringbeans.config;

import com.example.myspringbeans.factory.BeanFactory;

/**
 * @author julu
 * @date 2022/9/18 13:05
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 没有外部定义的自动装配
     */
    int AUTOWIRE_NO = 0;
}
