package com.example.myspringbeans.context.support;

import com.example.myspringbeans.config.BeanPostProcessor;
import com.example.myspringbeans.config.EmbeddedValueResolver;
import com.example.myspringbeans.context.ConfigurableApplicationContext;
import com.myspringcore.util.StringValueResolver;

/**
 * @author julu
 * @date 2022/9/18 11:53
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ConfigurableApplicationContext applicationContext;

    private final StringValueResolver embeddedValueResolver;

    public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
    }
}
