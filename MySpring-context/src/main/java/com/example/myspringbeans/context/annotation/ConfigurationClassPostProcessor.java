package com.example.myspringbeans.context.annotation;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.BeanDefinitionHolder;
import com.example.myspringbeans.factory.config.BeanDefinition;
import com.example.myspringbeans.factory.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.support.BeanDefinitionRegistry;
import com.example.myspringbeans.support.BeanDefinitionRegistryPostProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    private final Log logger = LogFactory.getLog(getClass());

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
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if (beanDefinition.getAttribute(ConfigurationClassUtils.CONFIGURATION_CLASS_ATTRIBUTE) != null){
                if (logger.isDebugEnabled()){
                    logger.debug("Bean definition has already bean processed as a configuration class: " + beanName);
                }
            }
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }
}
