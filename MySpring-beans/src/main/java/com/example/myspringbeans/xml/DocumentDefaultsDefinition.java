package com.example.myspringbeans.xml;

import com.example.myspringbeans.factory.parsing.DefaultsDefinition;
import org.springframework.lang.Nullable;

/**
 * @author julu
 * @date 2022/12/4 13:09
 */
public class DocumentDefaultsDefinition implements DefaultsDefinition {

    @Nullable
    private String lazyInit;

    @Nullable
    private String merge;

    @Nullable
    private String autowire;

    @Nullable
    private String autowireCandidates;

    @Nullable
    private String initMethod;

    @Nullable
    private String destroyMethod;

    @Nullable
    private Object source;

    @Override
    public Object getSource() {
        return null;
    }

    @Nullable
    public String getLazyInit() {
        return this.lazyInit;
    }

    public void setLazyInit(String lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getMerge() {
        return this.merge;
    }

    public void setMerge(String merge){
        this.merge = merge;
    }

    @Nullable
    public String getAutowire() {
        return autowire;
    }

    public void setAutowire(@Nullable String autowire) {
        this.autowire = autowire;
    }

    @Nullable
    public String getAutowireCandidates() {
        return autowireCandidates;
    }

    public void setAutowireCandidates(@Nullable String autowireCandidates) {
        this.autowireCandidates = autowireCandidates;
    }

    @Nullable
    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(@Nullable String initMethod) {
        this.initMethod = initMethod;
    }

    @Nullable
    public String getDestroyMethod() {
        return destroyMethod;
    }

    public void setDestroyMethod(@Nullable String destroyMethod) {
        this.destroyMethod = destroyMethod;
    }

    public void setSource(@Nullable Object source) {
        this.source = source;
    }
}
