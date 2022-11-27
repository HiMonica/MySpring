package com.example.myspringbeans.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

import javax.swing.text.Document;

/**
 * documentLoader的默认实现类
 *
 * @author julu
 * @date 2022/11/27 14:56
 */
public class DefaultDocumentLoader implements DocumentLoader{



    @Override
    public Document loadDocument(InputSource inputSource, EntityResolver entityResolver, ErrorHandler errorHandler, int validationMode, boolean namespaceAware) throws Exception {
        return null;
    }
}
