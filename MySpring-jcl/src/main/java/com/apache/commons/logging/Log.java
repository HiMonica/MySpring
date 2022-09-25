package com.apache.commons.logging;

/**
 * @author julu
 * @date 2022/9/4 15:48
 */
public interface Log {

    /**
     * 是否启动debug日志级别
     * @return
     */
    boolean isDebugEnabled();

    /**
     * 是否启动trace日志级别
     * @return
     */
    boolean isTraceEnabled();

    /**
     * 记录trace级别日志
     * @param message
     */
    void trace(Object message);

    /**
     * 记录debug级别日志
     * @param message
     */
    void debug(Object message);
}
