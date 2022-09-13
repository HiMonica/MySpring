package com.example.myspringbeans.config;

import com.example.myspringbeans.BeansException;
import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/9/13 23:26
 */
public interface BeanExpressionResolver {

    @Nullable
    Object evaluate(@Nullable String value, BeanExpressionContext evalContext) throws BeansException;
}
