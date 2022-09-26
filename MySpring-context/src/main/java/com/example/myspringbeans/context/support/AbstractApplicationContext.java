package com.example.myspringbeans.context.support;

import com.apache.commons.logging.Log;
import com.apache.commons.logging.LogFactory;
import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.context.ApplicationEvent;
import com.example.myspringbeans.context.ApplicationListener;
import com.example.myspringbeans.context.ConfigurableApplicationContext;
import com.example.myspringbeans.context.EnvironmentAware;
import com.example.myspringbeans.context.expression.StandardBeanExpressionResolver;
import com.example.myspringbeans.context.weaving.ApplicationContext;
import com.example.myspringbeans.factory.BeanFactory;
import com.example.myspringbeans.factory.config.BeanFactoryPostProcessor;
import com.example.myspringbeans.support.ResourceEditorRegistrar;
import com.myspringcore.core.env.ConfigurableEnvironment;
import com.myspringcore.core.env.StandardEnvironment;
import com.myspringcore.core.io.DefaultResourceLoader;
import com.myspringcore.core.io.ResourceLoader;
import com.myspringcore.core.io.support.PathMatchingResourcePatternResolver;
import com.myspringcore.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    /**
     * 刷新和销毁的同步监视器
     */
    private final Object startupShutdownMonitor = new Object();

    /**
     * 设置上下文启动的时间戳
     */
    private long startupDate;

    /**
     * 展示的名称
     */
    // TODO: 2022/9/11 工具暂时先用spring源码
    private String displayName = ObjectUtils.identityToString(this);

    /**
     * 是否关闭
     */
    private final AtomicBoolean closed = new AtomicBoolean();

    /**
     * 是否活跃
     */
    private final AtomicBoolean active = new AtomicBoolean();

    /**
     * 指定监听器
     */
    private final Set<ApplicationListener<?>> applicationListeners = new LinkedHashSet<>();

    /**
     * 本地监听器，需要在初始化的时候refresh
     */
    @Nullable
    private Set<ApplicationListener<?>> earlyApplicationListeners;

    /**
     * 如果这个id存在，那么他一定要是唯一
     */
    private String id = ObjectUtils.identityToString(this);

    @Nullable
    private ResourcePatternResolver resourcePatternResolver;

    @Nullable
    private ApplicationContext parent;

    @Nullable
    private Set<ApplicationEvent> earlyApplicationEvents;

    /**
     * 工厂中的LoadTimeWeaver bean的名称。如果提供了这样的bean，
     * 上下文将使用一个临时ClassLoader进行类型匹配，按顺序允许
     * LoadTimeWeaver处理所有实际的bean类。
     */
    String LOAD_TIME_WEAVER_BEAN_NAME = "loadTimeWeaver";

    /**
     * 工厂中System属性bean的名称
     */
    String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";

    /**
     * 工厂中系统环境bean的名称
     */
    String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";

    /**
     * 要在刷新是应用的BeanFactoryPostProcessors
     */
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    protected final Log logger = LogFactory.getLog(getClass());

    @Nullable
    private ConfigurableEnvironment environment;

    public AbstractApplicationContext() {
        this.resourcePatternResolver = getResourcePatternResolver();
    }

    protected ResourcePatternResolver getResourcePatternResolver(){
        return new PathMatchingResourcePatternResolver(this);
    }

    public AbstractApplicationContext(@Nullable ApplicationContext parent) {
        this();
        setParent(parent);
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor){
            //先将一些后面需要的变量准备好
            prepareRefresh();

            //类注册到bean factory
            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

            //bean工厂准备工作：主要是将一些必要的东西注册进去
            prepareBeanFactory(beanFactory);

            try {
                //bean工厂进行后置处理
                // TODO: 2022/9/26 可以自定义一个试试
                postProcessBeanFactory(beanFactory);

                //注册并执行beanFactoryPostProcessor
                invokeBeanFactoryPostProcessors(beanFactory);

                //注册beanPostProcessor 主体是bean，这一步中没有执行，至于注册动作
                registerBeanPostProcessors(beanFactory);

                //初始化消息资源

                //初始化时间传播器
            }

            catch (BeansException ex){

                //销毁已经创建的单例避免dangling处理
                destroyBeans();

                //重置active的状态
                cancelRefresh(ex);

                //将异常抛给调用者
                throw ex;
            }

            finally {

            }
        }
    }

    protected void prepareRefresh(){
        //1、设置开启的时间，以供日志使用
        this.startupDate = System.currentTimeMillis();

        //2、设置两个状态值，打开和活跃，因为并发的考虑，涉及到修改操作，并发考虑，两个状态值用AtomicBoolean
        this.closed.set(false);
        this.active.set(true);

        //3、spring启动的日志打印
        // TODO: 2022/9/11 logger是Slf4j的原因是因为我引入了Slf4j的包
        if (logger.isDebugEnabled()){
            if (logger.isTraceEnabled()){
                logger.trace("Refreshing" + this);
            }
            else {
                logger.debug("Refreshing" + getDisplayName());
            }
        }

        //4、初始化，包活后面要用到的environment
        initPropertySources();

        //5、校验
        getEnvironment().validateRequiredProperties();

        //6、一些字段的初始化，主要是监听
        // TODO: 2022/9/11 目前不知道是干嘛用的
        if (this.earlyApplicationListeners == null){
            this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListeners);
        }else {
            this.applicationListeners.clear();
            this.applicationListeners.addAll(this.earlyApplicationListeners);
        }

        this.earlyApplicationEvents = new LinkedHashSet<>();
    }

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory){
        //告诉bean工厂使用上下文的类加载器等
        beanFactory.setBeanClassLoader(getClassLoader());
        //指定表达式的解析策略
        beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
        //资源编辑器
        beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));


        //这一步中，给beanFactory注册了个beanPostProcessor，后处理器的类型是 ApplicationContextAwareProcessor
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        //下面是一些需要忽略的接口，都是一些继承Aware的接口
        beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
        // TODO: 2022/9/18 有些需要装载的类先后面用到再装载
//        beanFactory.ignoreDependencyInterface();

        //
        beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
        beanFactory.registerResolvableDependency(ResourceLoader.class, this);
        beanFactory.registerResolvableDependency(ApplicationContext.class, this);

        //将早期后处理器注册为applicationlistener，用于检测内部bean。
//        beanFactory.addBeanPostProcessor();

        //检车
        if (!beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)){
            //不存在这个名称的bean，注册
            beanFactory.registerSingleton(LOAD_TIME_WEAVER_BEAN_NAME, getEnvironment());
        }
        if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)){
            beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
        }
        if (!beanFactory.containsBean(SYSTEM_ENVIRONMENT_BEAN_NAME)){
            beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
        }
    }

    protected void initPropertySources(){
        //默认情况下什么都不做，用来初始化什么东西的
    }

    @Override
    public long getStartupDate() {
        return startupDate;
    }

    @Override
    public ConfigurableEnvironment getEnvironment() {
        if (this.environment == null){
            this.environment = createEnvironment();
        }
        return this.environment;
    }

    protected ConfigurableEnvironment createEnvironment(){
        return new StandardEnvironment();
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * 告诉子类刷新内部工厂
     * @return
     */
    protected ConfigurableListableBeanFactory obtainFreshBeanFactory(){
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected abstract void refreshBeanFactory();

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    protected void destroyBeans(){
        getBeanFactory().destroySingletons();
    }

    protected void cancelRefresh(BeansException ex){
        this.active.set(false);
    }

    protected abstract void closeBeanFactory();

    @Nullable
    protected BeanFactory getInternalParentBeanFactory(){
        return (getParent() instanceof ConfigurableApplicationContext ?
                ((ConfigurableApplicationContext) getParent()).getBeanFactory() : getParent());
    }

    @Override
    public ApplicationContext getParent() {
        return this.parent;
    }

    @Override
    public String getId() {
        return this.id;
    }

    /**
     * beanFactory后置处理，这里可以扩展，继承AbstractApplicationContext实现这个方法
     * @param beanFactory
     */
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory){

    }

    /**
     * 实例化并调用所有注册的BeanFactoryPostProcessor,给出明确的顺序,必须在单例实例化之前调用
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        // getBeanFactoryPostProcessors(); 获取当前应用上下文 beanFactoryPostProcessors变量
        // invokeBeanFactoryPostProcessors(); 实例化并调用所有已注册 BeanFactoryPostProcessor后处理器
        PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());

        //如果存在LOAD_TIME_WEAVER_BEAN_NAME，则加入对应的工厂钩子，并且加入一个自定义的ClassLoader
        if (beanFactory.getTempClassLoader() == null && beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)){
//            beanFactory.addBeanPostProcessor();
//            beanFactory.setTempClassLoader(new );
        }
    }

    /**
     * 获取当前应用上下文beanFactoryPostProcessor变量
     * @return
     */
    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors(){
        return this.beanFactoryPostProcessors;
    }

    /**
     * 实例化并注册所有BeanPostProcessor bean，如果给出明确的顺序。
     *
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);
    }
}
