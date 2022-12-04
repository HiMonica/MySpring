package com.example.myspringbeans.xml;

import com.example.myspringbeans.factory.config.BeanDefinition;
import org.springframework.lang.Nullable;

import java.util.Deque;

/**
 * @author julu
 * @date 2022/12/4 16:07
 */
public class ParserContext {

    private final XmlReaderContext readerContext;

    private final BeanDefinitionParserDelegate delegate;

    @Nullable
    private BeanDefinition containingBeanDefinition;

//    private final Deque<>

    public ParserContext(XmlReaderContext readerContext, BeanDefinitionParserDelegate delegate){
        this.readerContext = readerContext;
        this.delegate = delegate;
    }

    public ParserContext(XmlReaderContext readerContext, BeanDefinitionParserDelegate delegate,
                         @Nullable BeanDefinition containingBeanDefinition){
        this.readerContext = readerContext;
        this.delegate = delegate;
        this.containingBeanDefinition = containingBeanDefinition;
    }
}
