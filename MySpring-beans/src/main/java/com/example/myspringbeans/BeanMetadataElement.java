package com.example.myspringbeans;

import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/10/7 10:11
 */
public interface BeanMetadataElement {

    /**
     * 返回此元数据元素的配置源
     * @return
     */
    @Nullable
    Object getSource();
}
