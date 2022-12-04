package com.example.myspringbeans.factory.parsing;

import com.example.myspringbeans.xml.DocumentDefaultsDefinition;

import java.util.EventListener;

/**
 * @author julu
 * @date 2022/12/4 13:36
 */
public interface ReaderEventListener extends EventListener {

    /**
     * 已注册给定默认值的listener
     * @param defaultsDefinition
     */
    void defaultRegistered(DocumentDefaultsDefinition defaultsDefinition);
}
