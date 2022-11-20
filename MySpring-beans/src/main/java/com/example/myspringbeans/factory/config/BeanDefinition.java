package com.example.myspringbeans.factory.config;

import com.example.myspringbeans.BeanMetadataElement;
import com.myspringcore.core.AttributeAccessor;

/**
 * @author julu
 * @date 2022/9/24 11:56
 */
public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {

    /**
     *  获取这个角色的提示
     *
     * @return
     */
    int getRole();
}
