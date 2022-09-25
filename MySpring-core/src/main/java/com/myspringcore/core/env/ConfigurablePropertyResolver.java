package com.myspringcore.core.env;

import org.springframework.core.env.MissingRequiredPropertiesException;

/**
 * 用于将属性值转化
 *
 * @author julu
 * @date 2022/9/25 09:56
 */
public interface ConfigurablePropertyResolver extends PropertyResolver{

    /**
     * 所指定的属性值存在并转化为一个非null值
     *
     * @throws MissingRequiredPropertiesException
     */
    void validateRequiredProperties() throws MissingRequiredPropertiesException;
}
