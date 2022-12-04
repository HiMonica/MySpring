package com.example.myspringbeans.xml;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.w3c.dom.Document;


/**
 * 用于解析包含Spring bean定义的XML文档的SPI。
 *
 * @author julu
 * @date 2022/11/27 15:33
 */
public interface BeanDefinitionDocumentReader {

    void registerBeanDefinitions(Document doc, XmlReaderContext readerContext)
            throws BeanDefinitionStoreException;
}
