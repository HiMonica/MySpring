package com.example.myspringbeans.context.support;

import com.apache.commons.logging.Log;
import com.apache.commons.logging.LogFactory;
import com.example.myspringbeans.config.BeanPostProcessor;
import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.factory.config.BeanDefinition;
import com.example.myspringbeans.factory.config.BeanFactoryPostProcessor;
import org.springframework.lang.Nullable;

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

    public static void registerBeanPostProcessors(
            ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext){
        // 从注册表中取出class类型为BeanPostProcessor的bean名称列表
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);

        // 注册记录信息消息的BeanPostProcessorChecker
        // 在BeanPostProcessor实例化期间创建bean，即当
        // 一个bean没有资格被所有的beanPostProcessor处理。
        int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;

    }

    /**
     *
     */
    private static final class BeanPostProcessorChecker implements BeanPostProcessor{

        private static final Log logger = LogFactory.getLog(BeanPostProcessorChecker.class);

        private final ConfigurableListableBeanFactory beanFactory;

        private final int beanPostProcessorTargetCount;

        public BeanPostProcessorChecker(ConfigurableListableBeanFactory beanFactory, int beanPostProcessorTargetCount){
            this.beanFactory = beanFactory;
            this.beanPostProcessorTargetCount = beanPostProcessorTargetCount;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) {
            if (!(bean instanceof BeanPostProcessor) && !isInfrastructureBean(beanName) &&
                    this.beanFactory.getBeanPostProcessorCount() < this.beanPostProcessorTargetCount){
                if (logger.isInfoEnabled()) {
                    logger.info("Bean '" + beanName + "' of type [" + bean.getClass().getName() +
                            "] is not eligible for getting processed by all BeanPostProcessors " +
                            "(for example: not eligible for auto-proxying)");
                }
            }
            return bean;
        }

        private boolean isInfrastructureBean(@Nullable String beanName){
            if (beanName != null && this.beanFactory.containsBeanDefinition(beanName)){
                BeanDefinition bd = this.beanFactory.getBeanDefinition(beanName);
//                return (bd.getRole() == );
            }
            return false;
        }
    }
}
