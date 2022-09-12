package org.springframework.context.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.ConfigurableListableBeanFactory;
import com.example.myspringbeans.factory.BeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.weaving.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashSet;
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

            //准备bean工厂

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
}
