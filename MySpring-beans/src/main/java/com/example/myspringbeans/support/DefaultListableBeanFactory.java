package com.example.myspringbeans.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.PropertyEditorRegistrar;
import com.example.myspringbeans.TypeConverter;
import com.example.myspringbeans.config.BeanExpressionResolver;
import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.config.Scope;
import com.example.myspringbeans.factory.BeanFactory;
import com.example.myspringbeans.factory.config.BeanDefinition;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.ResolvableType;
import com.myspringcore.core.convert.ConversionService;
import org.springframework.lang.Nullable;
import com.myspringcore.util.StringValueResolver;
import org.springframework.util.ClassUtils;

import java.beans.PropertyEditor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * beanFactory初始化默认的实现类，是一个成熟的实现类
 * 基于bean定义元数据，可通过后处理器扩展
 *
 * @author julu
 * @date 2022/9/11 09:45
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {

    @Nullable
    private static Class<?> javaInjectProviderClass;

    static {
        try {
            javaInjectProviderClass =
                    ClassUtils.forName("javax.inject.Provider", DefaultListableBeanFactory.class.getClassLoader());
        }
        catch (ClassNotFoundException ex){
            // JSR-330 API不可用-提供者接口不支持。
            javaInjectProviderClass = null;
        }
    }

    /**
     * 从序列化id映射到工厂实例
     */
    private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories =
            new ConcurrentHashMap<>(8);

    /**
     * 此工厂的可选id，用于序列化目的
     */
    @Nullable
    private String serializationId;

    /**
     * 是否允许重新注册具有相同名称的不同定义
     */
    private Boolean allowBeanDefinitionOverriding = true;

    /**
     * 是否自动尝试解析bean之间的循环引用
     */
    private Boolean allowCircularReferences = true;

    /**
     * 可选的OrderComparator用于依赖列表和数组
     */
    @Nullable
    private Comparator<Object> dependencyComparator;



    public DefaultListableBeanFactory() {
        super();
    }

    public DefaultListableBeanFactory(@Nullable BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    public void setSerializationId(@Nullable String serializationId){
        if (serializationId != null){
            // TODO: 2022/9/11 为什么此处要用弱引用呢？
            serializableFactories.put(serializationId, new WeakReference<>(this));
        }
        else if (this.serializationId != null){
            serializableFactories.remove(this.serializationId);
        }
        this.serializationId = serializationId;

    }

    public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
        this.allowBeanDefinitionOverriding = allowBeanDefinitionOverriding;
    }

    public Boolean isAllowBeanDefinitionOverriding() {
        return allowBeanDefinitionOverriding;
    }

    public void setAllowCircularReferences(boolean allowCircularReferences) {
        this.allowCircularReferences = allowCircularReferences;
    }

    @Override
    public void setCacheBeanMetaData(boolean cacheBeanMetaData) {

    }

    @Override
    public boolean isCacheBeanMetaData() {
        return false;
    }

    @Override
    public BeanExpressionResolver getBeanExpressionResolver() {
        return null;
    }

    @Override
    public void setConversionService(ConversionService conversionService) {

    }

    @Override
    public ConversionService getConversionService() {
        return null;
    }

    @Override
    public void registerCustomEditor(Class<?> requiredType, Class<? extends PropertyEditor> propertyEditorClass) {

    }

    @Override
    public void copyRegisteredEditorsTo(PropertyEditorRegistrar register) {

    }

    @Override
    public void setTypeConverter(TypeConverter typeConverter) {

    }

    @Override
    public TypeConverter getTypeConverter() {
        return null;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {

    }

    @Override
    public boolean hasEmbeddedValueResolver() {
        return false;
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        return null;
    }

    @Override
    public int getBeanPostProcessorCount() {
        return 0;
    }

    @Override
    public void registerScope(String scopeName, Scope scope) {

    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {

    }

    @Override
    public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        return null;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public int getBeanDefinitionCount() {
        return 0;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public int getBeanDefinitionCont() {
        return 0;
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        return false;
    }

    @Override
    public String[] getBeanNamesForType(ResolvableType type) {
        return new String[0];
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return new String[0];
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return new String[0];
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return null;
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return null;
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return new String[0];
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return null;
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
        return null;
    }

    @Override
    public void ignoreDependencyType(Class<?> type) {

    }

    @Override
    public void ignoreDependencyInterface(Class<?> ifc) {

    }

    @Override
    public void registerResolvableDependency(Class<?> dependencyType, Object autowiredValue) {

    }

    @Override
    public Iterable<String> getBeanNameIterator() {
        return null;
    }

    @Override
    public void clearMetadataCache() {

    }

    @Override
    public void freezeConfiguration() {

    }

    @Override
    public boolean isConfigurationFrozen() {
        return false;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {

    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        super.registerSingleton(beanName, singletonObject);
    }
}
