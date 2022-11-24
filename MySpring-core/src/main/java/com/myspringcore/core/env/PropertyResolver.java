package com.myspringcore.core.env;

/**
 * 根据任意数据源解析源
 */
public interface PropertyResolver {

    boolean containsProperty(String key);

    String resolveRequirePlaceholders(String text) throws IllegalArgumentException;
}
