package com.myspringcore.core;

import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/10/7 10:22
 */
public interface AttributeAccessor {

    @Nullable
    Object getAttribute(String name);
}
