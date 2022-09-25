package com.myspringcore.context.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.support.DefaultListableBeanFactory;
import com.myspringcore.context.weaving.ApplicationContext;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * @author julu
 * @date 2022/9/11 14:37
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    public AbstractXmlApplicationContext() {
    }

    public AbstractXmlApplicationContext(@Nullable ApplicationContext parent){
        super(parent);
    }

    // TODO: 2022/9/11 太顶层了，大部分都是实现类了
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {

    }
}
