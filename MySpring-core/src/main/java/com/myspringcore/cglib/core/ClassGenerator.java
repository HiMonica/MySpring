package com.myspringcore.cglib.core;

import org.springframework.cglib.core.DebuggingClassWriter;

/**
 * @author julu
 * @date 2022/9/11 23:16
 */
public interface ClassGenerator {
    void generateClass(DebuggingClassWriter cw);
}
