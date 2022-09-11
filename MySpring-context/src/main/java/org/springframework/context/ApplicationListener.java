package org.springframework.context;

import java.util.EventListener;

/**
 * @author julu
 * 函数式接口
 * @date 2022/9/11 09:13
 */
@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理应用程序事件
     */
    void onApplicationEvent();
}
