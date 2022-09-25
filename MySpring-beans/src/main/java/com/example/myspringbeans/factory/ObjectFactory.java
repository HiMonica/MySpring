package com.example.myspringbeans.factory;

import com.example.myspringbeans.BeansException;

/**
 * 定义一个可以返回Object实例的工厂(可能是共享的或独立的)。
 *
 * @author julu
 * @date 2022/9/25 11:48
 */
@FunctionalInterface
public interface ObjectFactory<T> {

    /**
     * 返回一个此工厂的实例对象
     *
     * @return
     * @throws BeansException
     */
    T getObject() throws BeansException;
}
