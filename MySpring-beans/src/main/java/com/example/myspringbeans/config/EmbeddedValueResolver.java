package com.example.myspringbeans.config;

import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * @author julu
 * @date 2022/9/18 12:01
 */
public class EmbeddedValueResolver implements StringValueResolver {

    @Nullable
    private final BeanExpressionResolver exprResolver;

    private final BeanExpressionContext exprContext;

    public EmbeddedValueResolver(ConfigurableBeanFactory beanFactory) {
        this.exprContext = new BeanExpressionContext(beanFactory, null);
        this.exprResolver = beanFactory.getBeanExpressionResolver();
    }

    @Override
    public String resolveStringValue(String strVal) {
        return null;
    }
}
