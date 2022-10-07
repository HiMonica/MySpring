package com.example.myspringbeans.context.annotation;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.BeanDefinitionHolder;
import com.example.myspringbeans.factory.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.support.BeanDefinitionRegistry;
import com.example.myspringbeans.support.BeanDefinitionRegistryPostProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author julu
 * @date 2022/10/7 10:05
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private final Set<Integer> registriesPostProcessed = new HashSet<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory registry) throws BeansException {
        int registryId = System.identityHashCode(registry);
        if (this.registriesPostProcessed.contains(registryId)){
            throw new IllegalStateException(
                    "postProcessBeanFactory already called on this post-processor against " + registry);
        }
        this.registriesPostProcessed.add(registryId);

        processConfigBeanDefinitions(registry);
    }

    /**
     * 注册表构建并配置验证模型
     * @param registry
     */
    private void processConfigBeanDefinitions(ConfigurableListableBeanFactory registry) {
        List<BeanDefinitionHolder> configCandidates = new ArrayList<>();
        String[] candidateNames = registry.getBeanDefinitionNames();

        for (String beanName : candidateNames) {

        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }
}
