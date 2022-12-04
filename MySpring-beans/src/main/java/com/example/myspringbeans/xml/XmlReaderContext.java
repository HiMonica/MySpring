package com.example.myspringbeans.xml;

import com.example.myspringbeans.factory.parsing.ReaderContext;
import com.myspringcore.core.io.Resource;

/**
 * @author julu
 * @date 2022/11/27 15:34
 */
public class XmlReaderContext extends ReaderContext {

    private final XmlBeanDefinitionReader reader;

    public XmlReaderContext(Resource resource, XmlBeanDefinitionReader reader){
        this.reader = reader;
    }
}
