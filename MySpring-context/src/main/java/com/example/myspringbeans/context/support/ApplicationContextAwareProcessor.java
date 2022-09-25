package com.myspringcore.context.support;

import com.example.myspringbeans.config.BeanPostProcessor;
import com.example.myspringbeans.config.EmbeddedValueResolver;
import com.myspringcore.context.ConfigurableApplicationContext;
import com.myspringcore.util.StringValueResolver;

/**
 * @author julu
 * @date 2022/9/18 11:53
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final com.myspringcore.context.ConfigurableApplicationContext applicationContext;

    private final StringValueResolver embeddedValueResolver;

    public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
    }
}
