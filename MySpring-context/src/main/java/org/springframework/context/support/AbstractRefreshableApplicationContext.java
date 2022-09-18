package org.springframework.context.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.weaving.ApplicationContext;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * @author julu
 * @date 2022/9/11 09:39
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    @Nullable
    private Boolean allowBeanDefinitionOverriding;

    @Nullable
    private Boolean allowCircularReferences;

    /**
     * 内部bean工厂同步监视器
     */
    // TODO: 2022/9/11 感觉这个东西很多地方都会看到是干嘛的 ，应该是用来加锁用的
    private final Object beanFactoryMonitor = new Object();

    @Nullable
    private DefaultListableBeanFactory beanFactory;

    public AbstractRefreshableApplicationContext() {
    }

    public AbstractRefreshableApplicationContext(@Nullable ApplicationContext parent){
        super(parent);
    }

    /**
     * 关闭老的工厂，为上下文生命周期的下一个阶段初始化一个新的工厂
     */
    @Override
    protected final void refreshBeanFactory() {
        //判断是否有bean工厂，如果有就会清理掉，并且关闭
        if (hasBeanFactory()){
            //1、销毁bean工厂中的所有单例
            destroyBeans();
            //2、关闭工厂
            closeBeanFactory();
        }
        try {
            //获取新的beanFactory，实例化
            DefaultListableBeanFactory beanFactory = createBeanFactory();
            //设置SerializationId
            beanFactory.setSerializationId(getId());
            //给beanFactory设置一些初始值
            customizeBeanFactory(beanFactory);
            //开始加载（bean注册）
            loadBeanDefinitions(beanFactory);
            //由于beanFactory 是公共变量，存在多线程操作，所以加锁操作，避免混乱修改
            synchronized (this.beanFactoryMonitor){
                this.beanFactory = beanFactory;
            }
        } catch (IOException e) {
            throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), e);
        }
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        synchronized (this.beanFactoryMonitor){
            if (this.beanFactory == null){
                throw new IllegalStateException("BeanFactory not initialized or already closed - " +
                        "call 'refresh' before accessing beans via the ApplicationContext");
            }
        }
        return this.beanFactory;
    }

    protected final boolean hasBeanFactory(){
        synchronized (this.beanFactoryMonitor){
            return this.beanFactory != null;
        }
    }

    @Override
    protected void closeBeanFactory() {
        synchronized (this.beanFactoryMonitor){
            if (this.beanFactory != null){
                this.beanFactory.setSerializationId(null);
                this.beanFactory = null;
            }
        }
    }

    protected DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory(getInternalParentBeanFactory());
    }

    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory){
        if (this.allowBeanDefinitionOverriding != null){
            //默认false，不运行覆盖
            beanFactory.setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
        }
        if (this.allowCircularReferences != null){
            //默认false，不允许循环引用
            beanFactory.setAllowCircularReferences(this.allowCircularReferences);
        }
    }

    public void setAllowBeanDefinitionOverriding(@Nullable Boolean allowBeanDefinitionOverriding) {
        this.allowBeanDefinitionOverriding = allowBeanDefinitionOverriding;
    }

    public void setAllowCircularReferences(@Nullable Boolean allowCircularReferences) {
        this.allowCircularReferences = allowCircularReferences;
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
        throws BeansException, IOException;
}
