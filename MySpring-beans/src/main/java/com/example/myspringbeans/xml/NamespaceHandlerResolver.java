package com.example.myspringbeans.xml;

import org.springframework.lang.Nullable;

/**
 *
 *
 * @author julu
 * @date 2022/12/4 16:01
 */
@FunctionalInterface
public interface NamespaceHandlerResolver {

    @Nullable
    NamespaceHandler resolve(String namespaceUri);
}
