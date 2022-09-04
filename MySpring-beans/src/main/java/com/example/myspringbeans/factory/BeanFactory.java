package com.example.myspringbeans.factory;

import com.example.myspringbeans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

public interface BeanFactory {

    /**
     * 用于解除对{@link FactoryBean}实例的引用，并将其与
     * bean <i>由FactoryBean创建</i>。例如，如果bean命名为
     * 是一个FactoryBean，获取{@code &myJndiObject}
     * 将返回工厂，而不是由工厂返回的实例。
     */
    String FACTORY_BEAN_PREFIX = "&";

    /**
     * 返回指定bean的一个实例，该实例可以是共享的，也可以是独立的。
     * 这个方法允许Spring BeanFactory被用作
     * 单一或原型设计模式。调用方可以保留对的引用
     * 在单例bean的情况下返回对象。
     * <p>将别名转换回相应的规范bean名。
     * 如果不能在这个工厂实例中找到bean，将询问父工厂。
     * @param name 要检索的bean的名称
     * 返回bean的一个实例
     * 如果没有指定名称的bean， @throws NoSuchBeanDefinitionException
     * 如果不能获得bean，则@throws BeansException
     */
    Object getBean(String name) throws BeansException;

    /**
     * 返回指定bean的一个实例，该实例可以是共享的，也可以是独立的。
     * <p>的行为与{@link #getBean(String)}相同，但提供了类型的度量
     * 通过抛出BeanNotOfRequiredTypeException来安全，如果bean不是
     * 需要的类型。这意味着ClassCastException不能在类型转换时被抛出
     * 结果正确，就像使用{@link #getBean(String)}一样。
     * <p>将别名转换回相应的规范bean名。
     * 如果不能在这个工厂实例中找到bean，将询问父工厂。
     * @param name 要检索的bean的名称
     * @param requiredType bean必须匹配的类型;可以是接口或超类
     * 返回bean的一个实例
     * 如果没有这样的bean定义，@抛出NoSuchBeanDefinitionException
     * 如果bean不是所需的类型，@throws BeanNotOfRequiredTypeException
     * 如果不能创建bean，则@throws BeansException
     */
    <T> T getBean(String name,Class<T> requiredType) throws BeansException;

    /**
     * 返回指定bean的一个实例，该实例可以是共享的，也可以是独立的。
     * 允许指定显式构造函数参数/工厂方法参数，
     * 覆盖bean定义中指定的默认参数(如果有)。
     * @param name 要检索的bean的名称
     * @param 在使用显式参数创建bean实例时使用参数
     * (仅适用于创建新实例，而不是检索现有实例)
     * 返回bean的一个实例
     * 如果没有这样的bean定义，@抛出NoSuchBeanDefinitionException
     * 如果给出了参数，则@throws BeanDefinitionStoreException
     * 受影响的bean不是一个原型
     * 如果不能创建bean，则@throws BeansException
     * @since 2.5
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 返回唯一匹配给定对象类型的bean实例。
     * 该方法按类型进入{@link ListableBeanFactory}查找区域
     * 但也可能被翻译成传统的基于名称的按名查找
     * 给定类型的。对于跨bean集的更广泛的检索操作，
     * 使用{@link ListableBeanFactory}和/或{@link BeanFactoryUtils}。
     * @param requiredType bean必须匹配的类型;可以是接口或超类
     * @返回与所需类型匹配的单个bean的实例
     * 如果没有找到给定类型的bean， @throws NoSuchBeanDefinitionException
     * 如果找到给定类型的多个bean，则@throws NoUniqueBeanDefinitionException
     * 如果不能创建bean，则@throws BeansException
     * @since 3.0
     * @see ListableBeanFactory
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 返回指定bean的一个实例，该实例可以是共享的，也可以是独立的。
     * 允许指定显式构造函数参数/工厂方法参数，
     * 覆盖bean定义中指定的默认参数(如果有)。
     * 该方法按类型进入{@link ListableBeanFactory}查找区域
     * 但也可能被翻译成传统的基于名称的按名查找
     * 给定类型的。对于跨bean集的更广泛的检索操作，
     * 使用{@link ListableBeanFactory}和/或{@link BeanFactoryUtils}。
     * @param requiredType bean必须匹配的类型;可以是接口或超类
     * @param 在使用显式参数创建bean实例时使用参数
     * (仅适用于创建新实例，而不是检索现有实例)
     * 返回bean的一个实例
     * 如果没有这样的bean定义，@抛出NoSuchBeanDefinitionException
     * 如果给出了参数，则@throws BeanDefinitionStoreException
     * 受影响的bean不是一个原型
     * 如果不能创建bean，则@throws BeansException
     * @since 4.1
     */
    <T> T getBean(Class<T> requiredType, Object... args) throws BeansException;

    /**
     * 返回指定bean的提供者，允许延迟按需检索
     * 实例，包括可用性和惟一性选项。
     * @param requiredType bean必须匹配的类型;可以是接口或超类
     * @返回一个对应的提供程序句柄
     * @since 5.1
     * @see # getBeanProvider (ResolvableType)
     */
    <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType);

    /**
     * 返回指定bean的提供者，允许延迟按需检索
     * 实例，包括可用性和惟一性选项。
     * @param requiredType bean必须匹配的类型;可以是泛型类型声明。
     * 注意集合类型在这里是不支持的，与反射不同
     * 注入点。以编程方式检索匹配的bean列表
     * 特定类型，在这里和后面指定实际bean类型作为参数
     * 使用{@link ObjectProvider#orderedStream()}或它的惰性流/迭代选项。
     * @返回一个对应的提供程序句柄
     * @since 5.1
     * @see ObjectProvider # iterator ()
     * @see ObjectProvider #流()
     * @see ObjectProvider # orderedStream ()
     */
    <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType);

    /**
     * 这个bean工厂包含一个bean定义还是外部注册的单例
     * 实例与给定名称?
     * <p>如果给定的名称是一个别名，它将被转换回相应的
     * 规范bean名。
     * 如果这个工厂是分层的，如果bean不能，将询问任何父工厂
     * 可以在这个工厂实例中找到。
     * 如果找到匹配给定名称的bean定义或单例实例，
     * 这个方法将返回{@code true}，不管命名bean定义是具体的
     * 或抽象，或懒惰，或渴望，在范围内或不。因此，注意{@code true}
     * 该方法的返回值不一定表明{@link #getBean}
     * 将能够获得同名的实例。
     * @param name 要查询的bean的名称
     * @return 给定名称的bean是否存在
     */
    boolean containsBean(String name);

    /**
     * 这个bean是共享的单例吗?也就是说，将总是{@link #getBean}
     * 返回相同的实例?
     * 注意:这个返回{@code false}的方法没有明确表示
     * 独立实例。它指示可能对应的非单例实例
     * 指定作用域的bean。使用{@link #isPrototype}操作来显式地
     * 检查独立的实例。
     * <p>将别名转换回相应的规范bean名。
     * 如果不能在这个工厂实例中找到bean，将询问父工厂。
     * @param name 要查询的bean的名称
     * @return这个bean是否对应于一个单例实例
     * 如果没有给定名称的bean， @throws NoSuchBeanDefinitionException
     * @see # getBean
     * @see # isPrototype
     */
    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    /**
     * 这个bean是一个原型吗?也就是说，将总是返回{@link #getBean}
     * 独立实例?
     * 注意:这个返回{@code false}的方法没有明确表示
     * 一个单例对象。它表示可能对应的非独立实例
     * 指定作用域的bean。使用{@link #isSingleton}操作来显式地
     * 检查一个共享的单例实例。
     * <p>将别名转换回相应的规范bean名。
     * 如果不能在这个工厂实例中找到bean，将询问父工厂。
     * @param name 要查询的bean的名称
     * @返回这个bean是否总是交付独立的实例
     * 如果没有给定名称的bean， @throws NoSuchBeanDefinitionException
     * @since 2.0.3
     * @see # getBean
     * @see # isSingleton
     */
    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

    /**
     * 检查给定名称的bean是否与指定的类型匹配。
     * 更具体地说，检查给定名称是否有{@link #getBean}调用
     * 将返回一个可分配给指定目标类型的对象。
     * <p>将别名转换回相应的规范bean名。
     * 如果不能在这个工厂实例中找到bean，将询问父工厂。
     * @param name 要查询的bean的名称
     * @param 类型匹配要匹配的类型(作为{@code ResolvableType})
     * 如果bean类型匹配，则@return {@code true}
     * {@code false}如果它不匹配或还不能确定
     * 如果没有给定名称的bean， @throws NoSuchBeanDefinitionException
     * @since 4.2
     * @see # getBean
     * @see # getType
     */
    boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException;

    boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;

    /**
     * 确定给定名称的bean的类型。更具体地说,
     * 确定{@link #getBean}将为给定名称返回的对象类型。
     * 对于{@link FactoryBean}，返回FactoryBean创建的对象类型，
     * 由{@link FactoryBean#getObjectType()}公开。
     * <p>将别名转换回相应的规范bean名。
     * 如果不能在这个工厂实例中找到bean，将询问父工厂。
     * @param name 要查询的bean的名称
     * @return bean的类型，如果不能确定，则为{@code null}
     * 如果没有给定名称的bean， @throws NoSuchBeanDefinitionException
     * @since 1.1.2
     * @see # getBean
     * @see # isTypeMatch
     */
    @Nullable
    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    /**
     * 返回给定bean名的别名(如果有的话)。
     * 当在{@link #getBean}调用中使用时，所有这些别名都指向相同的bean。
     * <p>如果给定的名称是别名，则对应的原始bean名称
     * 和其他别名(如果有的话)将与原始bean名一起返回
     * 表示数组中的第一个元素。
     * 如果不能在这个工厂实例中找到bean，将询问父工厂。
     * @param name bean名以检查别名
     * @return 返货别名，如果没有则返回空数组
     * @see # getBean
     */
    String[] getAliases(String name);

}
