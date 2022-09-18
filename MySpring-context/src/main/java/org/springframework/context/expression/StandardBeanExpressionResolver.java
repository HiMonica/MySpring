package org.springframework.context.expression;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.BeanExpressionContext;
import com.example.myspringbeans.config.BeanExpressionResolver;
import org.springframework.lang.Nullable;

/**
 * 使用Spring表达式解析Spring EL
 *
 * @author julu
 * @date 2022/9/18 08:18
 */
public class StandardBeanExpressionResolver implements BeanExpressionResolver {

    /**
     * 默认表达式的前缀
     */
    public static final String DEFAULT_EXPRESSION_PREFIX = "#{";

    /**
     * 默认表达式的后缀
     */
    public static final String DEFAULT_EXPRESSION_SUFFIX = "}";

    private String expressionPrefix = DEFAULT_EXPRESSION_PREFIX;

    private String expressionSuffix = DEFAULT_EXPRESSION_SUFFIX;

    public StandardBeanExpressionResolver() {
    }

    public StandardBeanExpressionResolver(@Nullable ClassLoader beanClassLoader){

    }

    @Override
    public Object evaluate(String value, BeanExpressionContext evalContext) throws BeansException {
        return null;
    }
}
