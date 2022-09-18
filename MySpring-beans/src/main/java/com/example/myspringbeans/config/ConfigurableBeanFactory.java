package com.example.myspringbeans.config;

import com.example.myspringbeans.PropertyEditorRegister;
import com.example.myspringbeans.TypeConverter;
import com.example.myspringbeans.factory.BeanFactory;
import com.example.myspringbeans.factory.HierarchicalBeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

import java.beans.PropertyEditor;

/**
 * @author julu
 * @date 2022/9/11 09:51
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {

    /**
     * 模式状态
     */
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 销毁工厂中的所有单例
     */
    void destroySingletons();

    /**
     * 设置此bean工厂的父组件
     * @param parentBeanFactory
     * @throws IllegalStateException
     */
    void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException;

    /**
     * 设置此bean工厂的类加载器
     * @param beanClassLoader
     */
    void setBeanClassLoader(@Nullable ClassLoader beanClassLoader);

    /**
     * 获取此bean工厂的类加载器
     * @return
     */
    @Nullable
    ClassLoader getBeanClassLoader();

    /**
     * 指定用于类型匹配目的的临时ClassLoader
     * @param tempClassLoader
     */
    void setTempClassLoader(@Nullable ClassLoader tempClassLoader);

    /**
     * 获取临时ClassLoader
     * @return
     */
    @Nullable
    ClassLoader getTempClassLoader();

    /**
     * 设置metaData参数
     * @param cacheBeanMetaData
     */
    void setCacheBeanMetaData(boolean cacheBeanMetaData);

    /**
     * 是否缓存metadata
     * @return
     */
    boolean isCacheBeanMetaData();

    /**
     * 为bean定义中的表达式指定解析策略
     * @param resolver
     */
    void setBeanExpressionResolver(@Nullable BeanExpressionResolver resolver);

    /**
     * 返回bean定义中的表达式指定的解析策略
     * @return
     */
    @Nullable
    BeanExpressionResolver getBeanExpressionResolver();

    /**
     * 设置类型转化类
     * @param conversionService
     */
    void setConversionService(@Nullable ConversionService conversionService);

    /**
     * 获取类型转化类
     * @return
     */
    @Nullable
    ConversionService getConversionService();

    /**
     * 添加一个应用于所有bean创建过程的propertyEditorRegister
     *
     * @param register
     */
    void addPropertyEditorRegistrar(PropertyEditorRegister register);

    /**
     * 类的所有属性注册给定的自定义属性编辑器
     *
     * @param requiredType
     * @param propertyEditorClass
     */
    void registerCustomEditor(Class<?> requiredType, Class<? extends PropertyEditor> propertyEditorClass);

    /**
     * 使用自定义编辑器初始化指定的PropertyEditorRegister
     *
     * @param register
     */
    void copyRegisteredEditorsTo(PropertyEditorRegister register);

    /**
     * 设置类型转化器
     *
     * @param typeConverter
     */
    void setTypeConverter(TypeConverter typeConverter);

    /**
     * 获取此bean工厂的类型转化器
     *
     * @return
     */
    TypeConverter getTypeConverter();

    /**
     * 为嵌入的值(如注释属性)添加一个字符串解析器。
     *
     * @param valueResolver
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 确定内嵌值解析器是否已就此注册
     *
     * @return
     */
    boolean hasEmbeddedValueResolver();

    @Nullable
    String resolveEmbeddedValue(String value);

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    int getBeanPostProcessorCount();

    void registerScope(String scopeName, Scope scope);

}
