package com.example.myspringbeans.xml;

import com.example.myspringbeans.support.AbstractBeanDefinitionReader;
import com.example.myspringbeans.support.BeanDefinitionReader;
import com.example.myspringbeans.support.BeanDefinitionRegistry;
import com.myspringcore.core.env.Environment;
import com.myspringcore.core.io.Resource;
import com.myspringcore.core.io.ResourceLoader;
import org.springframework.beans.factory.BeanDefinitionStoreException;

/**
 * xml形式的BeanDefinitionReader
 *
 * @author julu
 * @date 2022/11/20 17:31
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        super(registry);
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return null;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return null;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return null;
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
        return 0;
    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    public void setValidating(boolean validating){

    }
}
