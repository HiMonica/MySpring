package com.myspringcore.context.support;

import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.factory.config.BeanFactoryPostProcessor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        //1、调用BeanDefinitionRegistryPostProcessors，if存在
        Set<String> processedBeans = new HashSet<>();
        //beanFactory 默认使用的是DefaultListableBeanFactory，属于BeanDefinitionRegistry

    }
}
