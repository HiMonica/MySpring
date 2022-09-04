package com.example.myspringbeans;

import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

public abstract class BeansException extends NestedRuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }

}
