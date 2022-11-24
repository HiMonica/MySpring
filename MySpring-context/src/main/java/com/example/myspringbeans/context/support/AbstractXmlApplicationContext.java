package com.example.myspringbeans.context.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.context.weaving.ApplicationContext;
import com.example.myspringbeans.support.DefaultListableBeanFactory;
import com.example.myspringbeans.xml.ResourceEntityResolver;
import com.example.myspringbeans.xml.XmlBeanDefinitionReader;
import com.myspringcore.core.io.Resource;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * 通过xml的方式加载bean
 *
 * @author julu
 * @date 2022/9/11 14:37
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

    private boolean validating = true;

    public AbstractXmlApplicationContext() {
    }

    public AbstractXmlApplicationContext(@Nullable ApplicationContext parent){
        super(parent);
    }

    // TODO: 2022/9/11 太顶层了，大部分都是实现类了
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        // Create a new XmlBeanDefinitionReader for the given BeanFactory
        //创建一个xml文件的阅读器
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        //使用此上下文配置bean定义阅读器
        // TODO: 2022/11/24 目前不知道设置这个来干嘛
        //1、
        beanDefinitionReader.setEnvironment(this.getEnvironment());
        //2、设置资源加载器，就是该类
        beanDefinitionReader.setResourceLoader(this);
        // TODO: 2022/11/24 目前不知道设置这个来干嘛
        //3、
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

        // 允许子类提供reader的自定义初始化
        // 然后实际加载bean定义。(空方法，让子类进行扩展实现)
        //这里就相当于抽出一个方法去专门做reader的初始化
        initBeanDefinitionReader(beanDefinitionReader);
        //获取资源路径，然后去加载，资源路径在refresh之前就已经set进去了
        loadBeanDefinitions(beanDefinitionReader);
    }

    protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader){
        reader.setValidating(this.validating);
    }

    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException{
        Resource[] configResources = getConfigResources();
        if (configResources != null){
            reader.loadBeanDefinitions(configResources);
        }
        // 获取已经加载的资源文件路径（这些配置文件路径在第一步已经加载了）
        String[] configLocations = getConfigLocations();
        if (configLocations != null){
            reader.loadBeanDefinitions(configLocations);
        }
    }

    protected Resource[] getConfigResources(){
        return null;
    }
}
