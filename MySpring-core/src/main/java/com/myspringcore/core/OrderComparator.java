package com.myspringcore.core;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;

/**
 * 按顺序值递增，分别按优先级递减。
 *
 * @author julu
 * @date 2022/10/7 09:22
 */
public class OrderComparator implements Comparator<Object> {

    /**
     * 共享的默认实例
     */
    public static final OrderComparator INSTANCE = new OrderComparator();

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    private int doCompare(@Nullable Object o1, @Nullable Object o2, @Nullable OrderSourceProvider sourceProvider){
        boolean p1 = (o1 instanceof PriorityOrdered);
        boolean p2 = (o2 instanceof PriorityOrdered);
        if (p1 && !p2){
            return -1;
        }
        else if (p2 && !p1){
            return 1;
        }

        int i1 = getOrder(o1, sourceProvider);
        int i2 = getOrder(o2, sourceProvider);
        return Integer.compare(i1, i2);
    }


    private int getOrder(Object obj, OrderSourceProvider sourceProvider) {
        Integer order = null;
        if (obj != null && sourceProvider != null){
            Object orderSource = sourceProvider.getOrderSource(obj);
            if (orderSource != null){
                if (orderSource.getClass().isArray()){
                    Object[] sources = ObjectUtils.toObjectArray(orderSource);
                    for (Object source : sources) {
                        order = findOrder(source);
                        if (order != null){
                            break;
                        }
                    }
                }
                else {
                    order = findOrder(orderSource);
                }
            }
        }
        return (order != null ? order : getOrder(obj));
    }

    protected int getOrder(@Nullable Object obj){
        if (obj != null){
            Integer order = findOrder(obj);
            if (order != null){
                return order;
            }
        }
        return Ordered.LOWEST_PRECEDENCE;
    }

    protected Integer findOrder(Object obj) {
        return (obj instanceof Ordered ? ((Ordered) obj).getOrder() : null);
    }

    @FunctionalInterface
    public interface OrderSourceProvider{

        @Nullable
        Object getOrderSource(Object obj);
    }
}
