package com.example.myspringbeans.support;

import com.example.myspringbeans.PropertyEditorRegistrar;
import com.example.myspringbeans.config.BeanExpressionResolver;
import com.example.myspringbeans.config.BeanPostProcessor;
import com.example.myspringbeans.config.ConfigurableBeanFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author julu
 * @date 2022/9/11 15:44
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    @Nullable
    private BeanExpressionResolver beanExpressionResolver;

    @Nullable
    private ClassLoader beanCLassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * 自定义的propertyEditorRegistrars以应用此工厂的bean
     */
    private final Set<PropertyEditorRegistrar> propertyEditorRegistrars = new LinkedHashSet<>(4);

    /**
     * 在createBean中应用的BeanPostProcessors
     */
    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    @Override
    public void setBeanExpressionResolver(@Nullable BeanExpressionResolver resolver) {
        this.beanExpressionResolver = resolver;
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanCLassLoader = (beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public void addPropertyEditorRegistrar(PropertyEditorRegistrar register) {
        Assert.notNull(register, "PropertyEditorRegistrar must not be null");
        this.propertyEditorRegistrars.add(register);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        Assert.notNull(beanPostProcessor, "BeanPostProcessor must not be null");
        //移除老的position，如果有
        this.beanPostProcessors.remove(beanPostProcessor);
        // TODO: 2022/9/18 跟踪它是否支持实例化/销毁
        this.beanPostProcessors.add(beanPostProcessor);
    }
}
