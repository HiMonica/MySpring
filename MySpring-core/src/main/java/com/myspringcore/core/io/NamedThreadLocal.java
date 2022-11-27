package com.myspringcore.core.io;

import org.springframework.util.Assert;

/**
 * ThreadLocal的子类，公开指定的名称，属性name就是该ThreadLocal的名称
 *
 * @author julu
 * @date 2022/11/27 14:37
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

    private final String name;

    public NamedThreadLocal(String name){
        Assert.hasText(name, "Name must not be empty");
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
