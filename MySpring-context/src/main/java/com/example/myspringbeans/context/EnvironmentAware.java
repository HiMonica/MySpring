package com.example.myspringbeans.context;

import com.example.myspringbeans.factory.Aware;
import com.myspringcore.core.env.Environment;

/**
 * @author julu
 * @date 2022/9/18 23:16
 */
public interface EnvironmentAware extends Aware {

    /**
     * 设置这个组件运行的code环境
     *
     * @param environment
     */
    void setEnvironment(Environment environment);
}
