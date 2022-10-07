package com.myspringcore.core;

/**
 * @author julu
 * @date 2022/10/6 16:34
 */
public interface Ordered {

    /**
     * 对于最高优先级有用的常量
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    /**
     * 最低优先级的有用常数
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    /**
     * 获取该对象的顺序值
     *
     * @return
     */
    int getOrder();
}
