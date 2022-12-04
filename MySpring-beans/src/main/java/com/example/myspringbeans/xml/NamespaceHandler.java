package com.example.myspringbeans.xml;

import com.example.myspringbeans.factory.config.BeanDefinition;
import com.example.myspringbeans.factory.config.BeanDefinitionHolder;
import org.springframework.lang.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * {@link DefaultBeanDefinitionDocumentReader}使用的基接口
 * 用于在Spring XML配置文件中处理自定义名称空间
 * 的实现被期望返回的实现
 *
 * @author julu
 * @date 2022/12/4 16:02
 */
public interface NamespaceHandler {

    void init();

    @Nullable
    BeanDefinition parse(Element element, ParserContext parserContext);

    /**
     * 解析指定的node，并装饰提供
     * @param source
     * @param definition
     * @param parserContext
     * @return
     */
    @Nullable
    BeanDefinitionHolder decorate(Node source, BeanDefinitionHolder definition, ParserContext parserContext);
}
