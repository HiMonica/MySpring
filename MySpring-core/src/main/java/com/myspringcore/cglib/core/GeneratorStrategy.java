package com.myspringcore.cglib.core;

/**
 * @author julu
 * @date 2022/9/11 23:33
 */
public interface GeneratorStrategy {
    byte[] generate(ClassGenerator var1) throws Exception;

    boolean equals(Object var1);
}