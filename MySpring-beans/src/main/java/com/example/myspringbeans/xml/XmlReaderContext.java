package com.example.myspringbeans.xml;

import com.example.myspringbeans.factory.parsing.ReaderContext;
import com.example.myspringbeans.factory.parsing.ReaderEventListener;
import com.example.myspringbeans.factory.parsing.SourceExtractor;
import com.myspringcore.core.env.Environment;
import com.myspringcore.core.io.Resource;

/**
 * @author julu
 * @date 2022/11/27 15:34
 */
public class XmlReaderContext extends ReaderContext {

    private final XmlBeanDefinitionReader reader;

    private final NamespaceHandlerResolver namespaceHandlerResolver;

    public XmlReaderContext(Resource resource,
                            ReaderEventListener eventListener, SourceExtractor sourceExtractor,
                            XmlBeanDefinitionReader reader, NamespaceHandlerResolver namespaceHandlerResolver){
        super(resource, eventListener, sourceExtractor);
        this.reader = reader;
        this.namespaceHandlerResolver = namespaceHandlerResolver;
    }

    public final Environment getEnvironment(){
        return this.reader.getEnvironment();
    }

    public final NamespaceHandlerResolver getNamespaceHandlerResolver(){
        return this.namespaceHandlerResolver;
    }
}
