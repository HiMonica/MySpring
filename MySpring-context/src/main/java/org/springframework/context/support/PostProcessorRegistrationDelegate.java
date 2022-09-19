package org.springframework.context.support;

import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.factory.config.BeanFactoryPostProcessor;

import java.util.List;

/**
 * AbstractApplicationContext的后处理器处理的委托
 *
 * @author julu
 * @date 2022/9/19 23:25
 */
final class PostProcessorRegistrationDelegate {

    private PostProcessorRegistrationDelegate(){

    }

    public static void invokeBeanFactoryPostProcessors(
            ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors){

    }
}
