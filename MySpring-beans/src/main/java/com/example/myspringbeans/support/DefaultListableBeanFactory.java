package com.example.myspringbeans.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.PropertyEditorRegister;
import com.example.myspringbeans.TypeConverter;
import com.example.myspringbeans.config.BeanExpressionResolver;
import com.example.myspringbeans.config.BeanPostProcessor;
import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.config.Scope;
import com.example.myspringbeans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

import java.beans.PropertyEditor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author julu
 * @date 2022/9/11 09:45
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, Serializable {

    /**
     * 是否允许重新注册具有相同名称的不同定义
     */
    private Boolean allowBeanDefinitionOverriding = true;

    /**
     * 是否自动尝试解析bean之间的循环引用
     */
    private Boolean allowCircularReferences = true;

    /**
     * serializationId映射到工厂实例中
     */
    private static final Map<String, Reference<DefaultListableBeanFactory>> serializableFactories =
            new ConcurrentHashMap<>(8);

    public DefaultListableBeanFactory() {
        super();
    }

    public DefaultListableBeanFactory(@Nullable BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    @Nullable
    private String serializationId;

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
    public void setBeanExpressionResolver(BeanExpressionResolver resolver) {

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
    public void addPropertyEditorRegistrar(PropertyEditorRegister register) {

    }

    @Override
    public void registerCustomEditor(Class<?> requiredType, Class<? extends PropertyEditor> propertyEditorClass) {

    }

    @Override
    public void copyRegisteredEditorsTo(PropertyEditorRegister register) {

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
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    @Override
    public int getBeanPostProcessorCount() {
        return 0;
    }

    @Override
    public void registerScope(String scopeName, Scope scope) {

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
}
