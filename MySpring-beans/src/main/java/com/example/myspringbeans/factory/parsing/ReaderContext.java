package com.example.myspringbeans.factory.parsing;

import com.example.myspringbeans.xml.DocumentDefaultsDefinition;
import com.myspringcore.core.io.Resource;
import org.springframework.lang.Nullable;
import org.w3c.dom.Element;

/**
 * @author julu
 * @date 2022/11/27 15:35
 */
public class ReaderContext {

    private final SourceExtractor sourceExtractor;

    private final ReaderEventListener eventListener;

    private final Resource resource;

    ReaderContext(Resource resource, ReaderEventListener eventListener, SourceExtractor sourceExtractor){
        this.sourceExtractor = sourceExtractor;
        this.resource = resource;
        this.eventListener = eventListener;
    }

    @Nullable
    public Object extractSource(Object sourceCandidate) {
        return this.sourceExtractor.extractSource(sourceCandidate, this.resource);
    }

    /**
     * Fire an defaults-registered event(触发一个默认注册事件)
     *
     * @param defaultsDefinition
     */
    public void fireDefaultsRegistered(DocumentDefaultsDefinition defaultsDefinition) {
        this.eventListener.defaultRegistered(defaultsDefinition);
    }
}
