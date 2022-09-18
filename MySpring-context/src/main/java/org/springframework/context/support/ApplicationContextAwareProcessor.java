package org.springframework.context.support;

import com.example.myspringbeans.config.BeanPostProcessor;
import com.example.myspringbeans.config.EmbeddedValueResolver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.weaving.ApplicationContext;
import org.springframework.util.StringValueResolver;

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
