package com.example.myspringbeans.support;

import com.example.myspringbeans.factory.BeanFactory;
import org.springframework.lang.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author julu
 * @date 2022/9/11 09:45
 */
public class DefaultListableBeanFactory {

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
        // TODO: 2022/9/11 需要继承另一个父类
        super();
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

}
