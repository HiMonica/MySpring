package com.example.myspringbeans.xml;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;


/**
 * 用户加载XML的策略接口
 *
 * @author julu
 * @date 2022/11/27 14:52
 */
public interface DocumentLoader {

    /**
     * 从提供的inputSource中加载document文档
     *
     * @param inputSource
     * @param entityResolver
     * @param errorHandler
     * @param validationMode
     * @param namespaceAware
     * @return
     * @throws Exception
     */
    Document loadDocument(
            InputSource inputSource, EntityResolver entityResolver,
            ErrorHandler errorHandler, int validationMode, boolean namespaceAware)
            throws Exception;
}
