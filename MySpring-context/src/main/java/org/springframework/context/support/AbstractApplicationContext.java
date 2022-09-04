package org.springframework.context.support;

import com.example.myspringbeans.BeansException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.lang.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    /**
     * 刷新和销毁的同步监视器
     */
    private final Object startupShutdownMonitor = new Object();

    /**
     * 设置上下文启动的时间戳
     */
    private long startupDate;

    /**
     * 是否关闭
     */
    private final AtomicBoolean closed = new AtomicBoolean();

    /**
     * 是否活跃
     */
    private final AtomicBoolean active = new AtomicBoolean();

    protected final Log logger = LogFactory.getLog(getClass());

    @Nullable
    private ConfigurableEnvironment environment;

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor){
            //先将一些后面需要的变量准备好
            prepareRefresh();

            //类注册到bean factory


        }
    }

    protected void prepareRefresh(){
        //1、设置开启的时间，以供日志使用
        this.startupDate = System.currentTimeMillis();

        //2、设置两个状态值，打开和活跃，因为并发的考虑，涉及到修改操作，并发考虑，两个状态值用AtomicBoolean
        this.closed.set(false);
        this.active.set(true);



        //3、设置spring专属日志
        // TODO: 2022/8/28 专门写一个日志类

        //4、初始化，包活后面要用到的environment
        initPropertySources();

        //5、校验
        getEnvironment().validateRequiredProperties();
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
}
