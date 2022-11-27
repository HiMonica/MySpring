package com.example.myspringbeans.xml;

import org.springframework.beans.factory.BeanDefinitionStoreException;

import javax.swing.text.Document;

/**
 * @author julu
 * @date 2022/11/27 15:39
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader{
    @Override
    public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) throws BeanDefinitionStoreException {

    }
}
