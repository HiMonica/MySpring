package com.example.myspringbeans;

/**
 * 注册自定义策略的接口
 *
 * @author julu
 * @date 2022/9/18 08:37
 */
public interface PropertyEditorRegister {

    /**
     * 注册
     * @param register
     */
    void registerCustomEditors(PropertyEditorRegister register);
}
