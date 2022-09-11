package com.example.myspringbeans.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.ConfigurableBeanFactory;
import com.example.myspringbeans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/9/11 15:43
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements ConfigurableBeanFactory {

    public AbstractAutowireCapableBeanFactory() {
        super();
    }

    public AbstractAutowireCapableBeanFactory(@Nullable BeanFactory parentBeanFactory){
        this();
        setParentBeanFactory(parentBeanFactory);
    }

    @Override
    public void destroySingletons() {

    }

    @Override
    public void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException {

    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {

    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return null;
    }

    @Override
    public void setTempClassLoader(ClassLoader tempClassLoader) {

    }

    @Override
    public ClassLoader getTempClassLoader() {
        return null;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return null;
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
        return null;
    }

    @Override
    public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return null;
    }

    @Override
    public String[] getAliases(String name) {
        return new String[0];
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return null;
    }

    @Override
    public boolean containsLocalBean(String name) {
        return false;
    }
}
