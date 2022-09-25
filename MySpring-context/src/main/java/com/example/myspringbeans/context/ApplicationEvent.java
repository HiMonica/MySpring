package com.example.myspringbeans.context;

import java.util.EventObject;

/**
 * @author julu
 * @date 2022/9/11 09:14
 */
public abstract class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 61142010398780880L;

    private final long timestamp;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
