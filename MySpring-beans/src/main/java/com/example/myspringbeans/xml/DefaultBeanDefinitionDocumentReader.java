package com.example.myspringbeans.xml;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 接口的默认实现 根据“spring-beans”DTD和XSD格式读取bean定义 (Spring的默认XML bean定义格式)。
 *
 * @author julu
 * @date 2022/11/27 15:39
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader{

    public static final String BEAN_ELEMENT = BeanDefinitionParserDelegate.BEAN_ELEMENT;

    public static final String NESTED_BEANS_ELEMENT = "beans";

    public static final String ALIAS_ELEMENT = "alias";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String ALIAS_ATTRIBUTE = "alias";

    public static final String IMPORT_ELEMENT = "import";

    public static final String RESOURCE_ATTRIBUTE = "resource";

    public static final String PROFILE_ATTRIBUTE = "profile";

    @Nullable
    private XmlReaderContext readerContext;

    @Nullable
    private BeanDefinitionParserDelegate delegate;

    @Override
    public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) throws BeanDefinitionStoreException {
        this.readerContext = readerContext;
        doRegisterBeanDefinitions(doc.getDocumentElement());
    }

    /**
     * 在给定的根元素中注册每个bean定义。
     */
    protected void doRegisterBeanDefinitions(Element root) {
        BeanDefinitionParserDelegate parent = this.delegate;
        this.delegate = createDelegate(getReaderContext(), root, parent);

        if (this.delegate.isDefaultNamespace(root)){
            String profileSpec = root.getAttribute(PROFILE_ATTRIBUTE);
            if (StringUtils.hasText(profileSpec)){
                String[] specifiedProfiles = StringUtils.tokenizeToStringArray(
                        profileSpec, BeanDefinitionParserDelegate.MULTI_VALUE_ATTRIBUTE_DELIMITERS);
                if (!getReaderContext().getEnvironment().acceptsProfiles(specifiedProfiles)){
                    // TODO: 2022/12/4 日志
                }
                return;
            }
        }
        // preProcess 和 postProcess 点进去会发现是空方法，着两个方法留给子类重载，体现了设计模式---模版模式
        preProcessXml(root);
        // 核心方法，解析doc元素
        parseBeanDefinitions(root, this.delegate);
        postProcessXml(root);
    }

    /**
     * 解析文档根级别的元素:
     * "import"， "alias"， "bean"
     * @param root 文档的DOM根元素
     */
    protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
        if (delegate.isDefaultNamespace(root)){
            // 1.12 遍历 doc 中的节点列表
            NodeList nl = root.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (node instanceof Element){
                    Element ele = (Element) node;
                    if (delegate.isDefaultNamespace(ele)){
                        // 1.13 识别出默认标签的 bean 注册
                        // 根据元素名称，调用不同的加载方法，注册bean
                        parseDefaultElement(ele, delegate);
                    }
                    else {
                        // 3.7 解析自定义标签的入口
                        delegate.parseCustomElement(ele);
                    }
                }
            }
        }
        else {
            delegate.parseCustomElement(root);
        }
    }

    protected void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {
        // 2.1 默认标签解析
        if (delegate.nodeNameEquals(ele, IMPORT_ELEMENT)){
            // 解析 import 标签

        }
        else if (delegate.nodeNameEquals(ele, ALIAS_ATTRIBUTE)){
            // 解析 alias 标签
        }
        else if (delegate.nodeNameEquals(ele, BEAN_ELEMENT)){
            // 解析 bean 标签
        }
        else if (delegate.nodeNameEquals(ele, NESTED_BEANS_ELEMENT)){
            // 解析 beans 标签，其实就是递归，重新对这个element下的标签进行注册解析
            doRegisterBeanDefinitions(ele);
        }
    }

    /**
     * 后置处理，留给子类重载
     * @param root
     */
    protected void postProcessXml(Element root) {

    }

    /**
     * 前置处理，留给子类重载
     * @param root
     */
    protected void preProcessXml(Element root) {

    }



    private BeanDefinitionParserDelegate createDelegate(
            XmlReaderContext readerContext, Element root, BeanDefinitionParserDelegate parentDelegate) {

        BeanDefinitionParserDelegate delegate = new BeanDefinitionParserDelegate(readerContext);
        delegate.initDefaults(root, parentDelegate);
        return delegate;
    }

    private XmlReaderContext getReaderContext() {
        Assert.state(this.readerContext != null, "No XmlReaderContext available");
        return this.readerContext;
    }
}
