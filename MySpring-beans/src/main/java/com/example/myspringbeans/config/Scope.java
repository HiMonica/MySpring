package com.example.myspringbeans.config;

import org.springframework.lang.Nullable;

import javax.naming.spi.ObjectFactory;

/**
 * @author julu
 *  策略接口
 * @date 2022/9/13 23:30
 */
public interface Scope {

    /**
     * 返回具有给定名称的对象
     * @param name
     * @param objectFactory
     * @return
     */
    Object get(String name, ObjectFactory objectFactory);

    /**
     * 删除给定名称的对象
     * @param name
     * @return
     */
    @Nullable
    Object remove(String name);

    /**
     * 注册一个回调函数
     * @param name
     * @param callback
     */
    void registerDestructionCallback(String name, Runnable callback);

    /**
     * 解析给定key的上下文
     * @param key
     * @return
     */
    @Nullable
    Object resolveContextualObject(String key);

    /**
     * 返回当前底层作用域的id
     * @return
     */
    @Nullable
    String getConversationId();
}
