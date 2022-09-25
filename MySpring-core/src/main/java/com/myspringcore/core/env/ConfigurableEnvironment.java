package com.myspringcore.core.env;

import java.util.Map;

/**
 * 配置接口
 *
 * @author julu
 * @date 2022/9/25 09:53
 */
public interface ConfigurableEnvironment extends Environment, ConfigurablePropertyResolver{

    /**
     * 返回SystemProperties的值
     *
     * @return
     */
    Map<String, Object> getSystemProperties();

    /**
     * 返回SystemEnvironment的值
     *
     * @return
     */
    Map<String, Object> getSystemEnvironment();


}
