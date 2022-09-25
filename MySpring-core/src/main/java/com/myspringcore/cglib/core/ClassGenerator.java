package org.springframework.cglib.core;

/**
 * @author julu
 * @date 2022/9/11 23:16
 */
public interface ClassGenerator {
    void generateClass(DebuggingClassWriter cw);
}
