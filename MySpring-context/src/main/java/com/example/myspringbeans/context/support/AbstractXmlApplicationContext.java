package com.example.myspringbeans.context.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.context.weaving.ApplicationContext;
import com.example.myspringbeans.support.DefaultListableBeanFactory;
import com.example.myspringbeans.xml.ResourceEntityResolver;
import com.example.myspringbeans.xml.XmlBeanDefinitionReader;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * 通过xml的方式加载bean
 *
 * @author julu
 * @date 2022/9/11 14:37
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    public AbstractXmlApplicationContext() {
    }

    public AbstractXmlApplicationContext(@Nullable ApplicationContext parent){
        super(parent);
    }

    // TODO: 2022/9/11 太顶层了，大部分都是实现类了
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        // Create a new XmlBeanDefinitionReader for the given BeanFactory
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        //使用此上下文配置bean定义阅读器
        beanDefinitionReader.setEnvironment(this.getEnvironment());
        beanDefinitionReader.setResourceLoader(this);
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));



    }
}
