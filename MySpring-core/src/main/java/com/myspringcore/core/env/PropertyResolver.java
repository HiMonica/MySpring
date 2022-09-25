package com.myspringcore.core.env;

/**
 * 根据任意数据源解析源
 */
public interface PropertyResolver {

    boolean containsProperty(String key);
}
