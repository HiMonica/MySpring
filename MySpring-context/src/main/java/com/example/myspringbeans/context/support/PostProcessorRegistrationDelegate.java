package com.example.myspringbeans.context.support;

import com.apache.commons.logging.Log;
import com.apache.commons.logging.LogFactory;
import com.example.myspringbeans.config.BeanPostProcessor;
import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.factory.config.BeanDefinition;
import com.example.myspringbeans.factory.config.BeanFactoryPostProcessor;
import com.example.myspringbeans.support.BeanDefinitionRegistry;
import com.example.myspringbeans.support.BeanDefinitionRegistryPostProcessor;
import com.example.myspringbeans.support.DefaultListableBeanFactory;
import com.myspringcore.core.OrderComparator;
import com.myspringcore.core.Ordered;
import com.myspringcore.core.PriorityOrdered;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * AbstractApplicationContext的后处理器处理的委托
 *
 * @author julu
 * @date 2022/9/19 23:25
 */
final class PostProcessorRegistrationDelegate {

    private PostProcessorRegistrationDelegate(){

    }

    /**
     *
     *
     * @param beanFactory
     * @param beanFactoryPostProcessors
     */
    public static void invokeBeanFactoryPostProcessors(
            ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors){

        //1、调用BeanDefinitionRegistryPostProcessors，if存在
        Set<String> processedBeans = new HashSet<>();
        //beanFactory 默认使用的是DefaultListableBeanFactory，属于BeanDefinitionRegistry
        if (beanFactory instanceof BeanDefinitionRegistry){
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            //两个后置处理器列表
            List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
            List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();

            for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
                if (postProcessor instanceof BeanDefinitionRegistryPostProcessor){
                    BeanDefinitionRegistryPostProcessor registryProcessor =
                            (BeanDefinitionRegistryPostProcessor) postProcessor;
                    registryProcessor.postProcessBeanDefinitionRegistry(registry);
                    registryProcessors.add(registryProcessor);
                }else {
                    regularPostProcessors.add(postProcessor);
                }
            }
            List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();

            String[] postProcessorNames =
                    beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, true);
            for (String postProcessorName : postProcessorNames) {
                if (beanFactory.isTypeMatch(postProcessorName, PriorityOrdered.class)){
                    currentRegistryProcessors.add(beanFactory.getBean(postProcessorName, BeanDefinitionRegistryPostProcessor.class));
                    processedBeans.add(postProcessorName);
                }
            }
            //对后置处理器进行排序
            // TODO: 2022/10/7 为什么要排序呢？
            sortPostProcessors(currentRegistryProcessors, beanFactory);
            registryProcessors.addAll(currentRegistryProcessors);
            //根据顺序执行所有后置处理器
            invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
            currentRegistryProcessors.clear();

            //接下来，调用实现Ordered的BeanDefinitionRegistryPostProcessors
            postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
            for (String ppName : postProcessorNames) {
                if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)){
                    currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                    processedBeans.add(ppName);
                }
            }
        }


    }

    /**
     * 调用给定的BeanDefinitionRegistryPostProcessor beans
     *
     * @param postProcessors
     * @param registry
     */
    private static void invokeBeanDefinitionRegistryPostProcessors(
            Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors, BeanDefinitionRegistry registry){

        for (BeanDefinitionRegistryPostProcessor postProcessor : postProcessors) {
            postProcessor.postProcessBeanDefinitionRegistry(registry);
        }
    }

    private static void sortPostProcessors(List<BeanDefinitionRegistryPostProcessor> postProcessors, ConfigurableListableBeanFactory beanFactory) {
        Comparator<Object> comparatorToUse = null;
        if (beanFactory instanceof DefaultListableBeanFactory){
            comparatorToUse = ((DefaultListableBeanFactory) beanFactory).getDependencyComparator();
        }
        if (comparatorToUse == null){
            comparatorToUse = OrderComparator.INSTANCE;
        }
        postProcessors.sort(comparatorToUse);
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
