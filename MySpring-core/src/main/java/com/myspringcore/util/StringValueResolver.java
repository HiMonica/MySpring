package com.myspringcore.util;

import org.springframework.lang.Nullable;

/**
 * 解析字符串值的普通策略接口
 *
 * @author julu
 * @date 2022/9/18 09:41
 */
@FunctionalInterface
public interface StringValueResolver {

    @Nullable
    String resolveStringValue(String strVal);
}
