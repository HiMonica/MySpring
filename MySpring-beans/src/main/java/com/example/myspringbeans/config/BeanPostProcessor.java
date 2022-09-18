package com.example.myspringbeans.config;

import com.example.myspringbeans.BeansException;
import org.springframework.lang.Nullable;

/**
 * 工厂钩子，允许自定义修改新bean实例， 例如，检查标记接口或用代理包装它们。
 *
 * @author julu
 * @date 2022/9/18 11:54
 */
public interface BeanPostProcessor {

    /**
     * 将此BeanPostProcessor运行到任何bean之前的给定的新bean实例
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
        return bean;
    }

    /**
     * 将此BeanPostProcessor运行到任何bean之后的给定的新bean实例
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
        return bean;
    }
}
