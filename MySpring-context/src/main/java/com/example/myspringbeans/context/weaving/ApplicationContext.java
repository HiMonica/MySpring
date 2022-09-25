package com.myspringcore.context.weaving;

import com.example.myspringbeans.factory.HierarchicalBeanFactory;
import com.example.myspringbeans.factory.ListableBeanFactory;
import com.myspringcore.core.env.EnvironmentCapable;
import com.myspringcore.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * 所有配置接口的集中接口，应该是最重要的接口之一
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory,
        EnvironmentCapable, ResourceLoader {

    /**
     * 返回此应用上下文的唯一id
     */
    @Nullable
    String getId();

    /**
     * 返回此上下文所属的已部署应用程序的名称
     */
    String getApplicationName();

    /**
     * 返回上下文的朋友的名称
     */
    String getDisplayName();

    /**
     * 返回首次加载此上下文的时间戳
     */
    long getStartupDate();

    /**
     * 返回父上下文
     */
    @Nullable
    ApplicationContext getParent();

    // TODO: 2022/8/14 还有一个接口的没有实现，预留
}
