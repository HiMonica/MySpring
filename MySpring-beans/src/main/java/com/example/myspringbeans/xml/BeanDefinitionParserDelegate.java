package com.example.myspringbeans.xml;

import com.example.myspringbeans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 用于解析XML bean定义的有状态委托类
 * 用于主解析器和任何扩展
 *
 * @author julu
 * @date 2022/12/4 12:52
 */
public class BeanDefinitionParserDelegate {

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String MULTI_VALUE_ATTRIBUTE_DELIMITERS = ",; ";

    /**
     * Value of a T/F attribute that represents true.
     * Anything else represents false. Case seNsItive.
     */
    public static final String TRUE_VALUE = "true";

    public static final String FALSE_VALUE = "false";

    public static final String DEFAULT_VALUE = "default";

    public static final String DESCRIPTION_ELEMENT = "description";

    public static final String AUTOWIRE_NO_VALUE = "no";

    public static final String AUTOWIRE_BY_NAME_VALUE = "byName";

    public static final String AUTOWIRE_BY_TYPE_VALUE = "byType";

    public static final String AUTOWIRE_CONSTRUCTOR_VALUE = "constructor";

    public static final String AUTOWIRE_AUTODETECT_VALUE = "autodetect";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String BEAN_ELEMENT = "bean";

    public static final String META_ELEMENT = "meta";

    public static final String ID_ATTRIBUTE = "id";

    public static final String PARENT_ATTRIBUTE = "parent";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String ABSTRACT_ATTRIBUTE = "abstract";

    public static final String SCOPE_ATTRIBUTE = "scope";

    private static final String SINGLETON_ATTRIBUTE = "singleton";

    public static final String LAZY_INIT_ATTRIBUTE = "lazy-init";

    public static final String AUTOWIRE_ATTRIBUTE = "autowire";

    public static final String AUTOWIRE_CANDIDATE_ATTRIBUTE = "autowire-candidate";

    public static final String PRIMARY_ATTRIBUTE = "primary";

    public static final String DEPENDS_ON_ATTRIBUTE = "depends-on";

    public static final String INIT_METHOD_ATTRIBUTE = "init-method";

    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";

    public static final String FACTORY_METHOD_ATTRIBUTE = "factory-method";

    public static final String FACTORY_BEAN_ATTRIBUTE = "factory-bean";

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String INDEX_ATTRIBUTE = "index";

    public static final String TYPE_ATTRIBUTE = "type";

    public static final String VALUE_TYPE_ATTRIBUTE = "value-type";

    public static final String KEY_TYPE_ATTRIBUTE = "key-type";

    public static final String PROPERTY_ELEMENT = "property";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String LOOKUP_METHOD_ELEMENT = "lookup-method";

    public static final String REPLACED_METHOD_ELEMENT = "replaced-method";

    public static final String REPLACER_ATTRIBUTE = "replacer";

    public static final String ARG_TYPE_ELEMENT = "arg-type";

    public static final String ARG_TYPE_MATCH_ATTRIBUTE = "match";

    public static final String REF_ELEMENT = "ref";

    public static final String IDREF_ELEMENT = "idref";

    public static final String BEAN_REF_ATTRIBUTE = "bean";

    public static final String PARENT_REF_ATTRIBUTE = "parent";

    public static final String VALUE_ELEMENT = "value";

    public static final String NULL_ELEMENT = "null";

    public static final String ARRAY_ELEMENT = "array";

    public static final String LIST_ELEMENT = "list";

    public static final String SET_ELEMENT = "set";

    public static final String MAP_ELEMENT = "map";

    public static final String ENTRY_ELEMENT = "entry";

    public static final String KEY_ELEMENT = "key";

    public static final String KEY_ATTRIBUTE = "key";

    public static final String KEY_REF_ATTRIBUTE = "key-ref";

    public static final String VALUE_REF_ATTRIBUTE = "value-ref";

    public static final String PROPS_ELEMENT = "props";

    public static final String PROP_ELEMENT = "prop";

    public static final String MERGE_ATTRIBUTE = "merge";

    public static final String QUALIFIER_ELEMENT = "qualifier";

    public static final String QUALIFIER_ATTRIBUTE_ELEMENT = "attribute";

    public static final String DEFAULT_LAZY_INIT_ATTRIBUTE = "default-lazy-init";

    public static final String DEFAULT_MERGE_ATTRIBUTE = "default-merge";

    public static final String DEFAULT_AUTOWIRE_ATTRIBUTE = "default-autowire";

    public static final String DEFAULT_AUTOWIRE_CANDIDATES_ATTRIBUTE = "default-autowire-candidates";

    public static final String DEFAULT_INIT_METHOD_ATTRIBUTE = "default-init-method";

    public static final String DEFAULT_DESTROY_METHOD_ATTRIBUTE = "default-destroy-method";

    private final XmlReaderContext readerContext;

    private final DocumentDefaultsDefinition defaults = new DocumentDefaultsDefinition();

    public BeanDefinitionParserDelegate(XmlReaderContext readerContext){
        Assert.notNull(readerContext, "XmlReaderContext must not be null");
        this.readerContext = readerContext;
    }

    public void initDefaults(Element root, BeanDefinitionParserDelegate parent) {
        // 准备好DocumentDefaultsDefinition defaults这个类里面的所有元素
        populateDefaults(this.defaults, (parent != null ? parent.defaults : null), root);
        // 将准备好的defaults注册进context中
        this.readerContext.fireDefaultsRegistered(this.defaults);
    }

    @Nullable
    public BeanDefinition parseCustomElement(Element ele) {
        return parseCustomElement(ele, null);
    }

    @Nullable
    public BeanDefinition parseCustomElement(Element ele, @Nullable BeanDefinition containingBd){
        // 3.8 1、找到命名空间
        String namespaceUri = getNamespaceURI(ele);
        if (namespaceUri == null){
            return null;
        }
        // 2、根据命名空间找到对应的 NamespaceHandler
        NamespaceHandler handler = this.readerContext.getNamespaceHandlerResolver().resolve(namespaceUri);
        if (handler == null){
            // TODO: 2022/12/4 日志
            return null;
        }
        // 3、调用自定义的 NamespaceHandler 进行解析
        return handler.parse(ele, new ParserContext(this.readerContext, this, containingBd));
    }

    /**
     * 用默认的lazy-init填充给定的DocumentDefaultsDefinition实例
     * 自动装配，依赖检查设置，初始化方法，销毁方法和合并设置。
     * 通过退回到{@code parentDefaults}来支持嵌套的'beans'元素用例
     * 在本地没有显式设置默认值的情况下。
     *
     */
    protected void populateDefaults(DocumentDefaultsDefinition defaults, @Nullable DocumentDefaultsDefinition parentDefaults, Element root) {
        String lazyInit = root.getAttribute(DEFAULT_LAZY_INIT_ATTRIBUTE);
        if (isDefaultValue(lazyInit)){
            // Potentially inherited from outer <beans> sections, otherwise falling back to false.
            lazyInit = (parentDefaults != null ? parentDefaults.getLazyInit() : FALSE_VALUE);
        }
        defaults.setLazyInit(lazyInit);

        String merge = root.getAttribute(DEFAULT_MERGE_ATTRIBUTE);
        if (isDefaultValue(merge)){
            // Potentially inherited from outer <beans> sections, otherwise falling back to false.
            merge = (parentDefaults != null ? parentDefaults.getMerge() : FALSE_VALUE);
        }
        defaults.setMerge(merge);

        String autowire = root.getAttribute(DEFAULT_AUTOWIRE_ATTRIBUTE);
        if (isDefaultValue(autowire)){
            // Potentially inherited from outer <beans> sections, otherwise falling back to false.
            autowire = (parentDefaults != null ? parentDefaults.getAutowire() : FALSE_VALUE);
        }
        defaults.setAutowire(autowire);

        if (root.hasAttribute(DEFAULT_AUTOWIRE_CANDIDATES_ATTRIBUTE)){
            defaults.setAutowireCandidates(root.getAttribute(DEFAULT_AUTOWIRE_CANDIDATES_ATTRIBUTE));
        }
        else if (parentDefaults != null){
            defaults.setAutowireCandidates(parentDefaults.getAutowireCandidates());
        }

        if (root.hasAttribute(DEFAULT_INIT_METHOD_ATTRIBUTE)){
            defaults.setInitMethod(root.getAttribute(DEFAULT_INIT_METHOD_ATTRIBUTE));
        }
        else if (parentDefaults != null){
            defaults.setInitMethod(parentDefaults.getInitMethod());
        }

        if (root.hasAttribute(DEFAULT_DESTROY_METHOD_ATTRIBUTE)) {
            defaults.setDestroyMethod(root.getAttribute(DEFAULT_DESTROY_METHOD_ATTRIBUTE));
        }
        else if (parentDefaults != null) {
            defaults.setDestroyMethod(parentDefaults.getDestroyMethod());
        }

        defaults.setSource(this.readerContext.extractSource(root));
    }

    /**
     * 获取所提供节点的名称空间URI
     * @param node
     * @return
     */
    @Nullable
    public String getNamespaceURI(Node node){
        return node.getNamespaceURI();
    }

    private boolean isDefaultValue(String value){
        return (DEFAULT_VALUE.equals(value) || "".equals(value));
    }

    public boolean isDefaultNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    public boolean isDefaultNamespace(Node node) {
        return isDefaultNamespace(getNamespaceURI(node));
    }
}
