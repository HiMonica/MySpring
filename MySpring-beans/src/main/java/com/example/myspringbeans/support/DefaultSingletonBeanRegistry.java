package com.example.myspringbeans.support;

import com.example.myspringbeans.BeansException;
import com.example.myspringbeans.config.SingletonBeanRegistry;
import com.example.myspringbeans.factory.ObjectFactory;
import com.myspringcore.core.SimpleAliasRegistry;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author julu
 * @date 2022/9/11 15:45
 */
public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    /**
     * 单例对象的缓存：key：bean名称 value：bean实例
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * 单例工厂的缓存：key：bean名称 value：对象工厂
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    /**
     * 早期单例对象的缓存：key：bean名称 value：bean实例
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**
     * 已注册单例的集合，包含按注册顺序排列的bean名称。
     */
    private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

    /**
     * 当前正在创建的bean的名称
     */
    private final Set<String> singletonsCurrentlyInCreation =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    /**
     * 当前在创建检查中排除的bean的名称。
     */
    private final Set<String> inCreationCheckExclusions =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    /**
     * 被抑制异常的列表，可用于关联相关原因
     */
    @Nullable
    private Set<Exception> suppressedExceptions;

    /**
     *  标志，指示我们当前是否在destroysingleton中
     */
    private boolean singletonCurrentlyInDestruction = false;

    /**
     * key：可丢弃实例的bean名称 value：可丢弃的bean实例
     */
    private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

    /**
     * 包含的bean名称之间的映射:bean名称到bean包含的bean名称集
     */
    private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);

    /**
     * 相关bean名称之间的映射:bean名称到相关bean名称集
     */
    private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    /**
     * 依赖的bean名称之间的映射:bean名称到bean依赖项的bean名称集
     */
    private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);


    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull(singletonObject, "Singleton object must not be null");
        //因为要对单例缓存加锁呢？因为要对singletonObjects进行判空校验
        synchronized (this.singletonObjects){
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null){
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    /**
     * 给定的单例对象添加对对应的单例缓存中
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName, Object singletonObject){
        //为什么又加一次锁呢？monitor的进入数+1
        synchronized (this.singletonObjects){
            this.singletonObjects.put(beanName, singletonObject);
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.add(beanName);
        }
    }

    @Override
    @Nullable
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    @Nullable
    protected Object getSingleton(String beanName, boolean allowEarlyReference){
        Object singletonObject = this.singletonObjects.get(beanName);
        //检查缓存中是否存在实例
        //如果为null，说明单例缓存中没有，需要去更早期的单例对象查看，如果更早期的也没有，就需要去单例工厂中拿
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)){
            //公共变量都需要加锁操作，避免多线程并发修改
            synchronized (this.singletonObjects){
                //如果此bean正在加载则不处理
                //判断更早起的缓存中是否存在该bean
                singletonObject = this.earlySingletonObjects.get(beanName);
                if (singletonObject == null && allowEarlyReference){
                    // TODO: 2022/9/25 循环依赖问题解决
                    //当某些方法需要提前初始化，调用addSingletonFactory方法将对应的
                    // objectFactory初始化策略存储在earlySingletonObjects，并且从singletonFactories移除
                    ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                    //如果不为null，说明存在，需要将实例进行移动
                    if (singletonFactory != null){
                        //从工厂中获取
                        singletonObject = singletonFactory.getObject();
                        //放入更早起的单例缓存
                        this.earlySingletonObjects.put(beanName, singletonFactory);
                        //从单例工厂中移除
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    /**
     * 返回在给定名称下注册的(原始)单例对象，如果还没有注册就注册一个新的
     * @param beanName
     * @param singletonFactory
     * @return
     */
    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory){
        Assert.notNull(beanName, "Bean name must not be null");
        //全局变量，加锁
        synchronized (this.singletonObjects){
            //检查是否已经被加载了，单例模式就是可以复用已经创建的bean
            Object singletonObject = singletonObjects.get(beanName);
            //没有被加载
            if (singletonObject == null){
                // TODO: 2022/9/25 没懂这步
                if (this.singletonCurrentlyInDestruction){
                    throw new BeanCreationNotAllowedException(beanName,
                            "Singleton bean creation not allowed while singletons of this factory are in destruction " +
                                    "(Do not request a bean from a BeanFactory in a destroy method implementation!)");
                }
                if (logger.isDebugEnabled()){
                    logger.debug("Creating shared instance of singleton bean '" + beanName + "'");
                }
                // 初始化前操作，校验是否 beanName 是否有别的线程在初始化，并加入初始化状态中
                beforeSingletonCreation(beanName);
                boolean newSingleton = false;
                //创建一个抑制异常的列表
                boolean recordSuppressedExceptions = (this.suppressedExceptions == null);
                if (recordSuppressedExceptions){
                    this.suppressedExceptions = new LinkedHashSet<>();
                }
                //初始化bean，这个就是刚才的回调接口调用的方法，实际执行的是creatBean方法
                try {
                    singletonObject = singletonFactory.getObject();
                    newSingleton = true;
                }
                catch (IllegalStateException ex) {
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null){
                        throw ex;
                    }
                }
                catch (BeanCreationException ex){
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null){
                        throw ex;
                    }
                }
                finally {
                    //关闭一个抑制异常的列表
                    if (recordSuppressedExceptions){
                        this.suppressedExceptions = null;
                    }
                    //初始化后的操作，移除初始化状态，于前面before操作对应
                    afterSingletonCreation(beanName);
                }
                if (newSingleton){
                    //加入缓存
                    addSingleton(beanName, singletonObject);
                }
            }
            return singletonObject;
        }
    }

    /**
     * 判断指定的bean是否在创建中
     * @param beanName
     * @return
     */
    public boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

    /**
     * 创建单例之前的回调
     *
     * @param beanName
     */
    protected void beforeSingletonCreation(String beanName){
        if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.add(beanName)){
            throw new BeanCurrentlyInCreationException(beanName);
        }
    }

    protected void afterSingletonCreation(String beanName){
        if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.remove(beanName)){
            throw new IllegalStateException("Singleton '" + beanName + "' isn't currently in creation");
        }
    }

    @Override
    public boolean containSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        synchronized (this.singletonObjects){
            return StringUtils.toStringArray(this.registeredSingletons);
        }
    }

    @Override
    public int getSingletonCount() {
        synchronized (this.singletonObjects){
            return this.registeredSingletons.size();
        }
    }

    /**
     * 将单例互斥对象公开给子类和外部合作者。
     *
     * @return
     */
    @Override
    public Object getSingletonMutex() {
        return this.singletonObjects;
    }
}
