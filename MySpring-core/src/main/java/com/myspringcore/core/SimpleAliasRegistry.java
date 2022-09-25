package com.myspringcore.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author julu
 * @date 2022/9/25 11:43
 */
public class SimpleAliasRegistry implements AliasRegistry{

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public void registerAlias(String name, String alias) {

    }

    @Override
    public void removeAlias(String alias) {

    }

    @Override
    public boolean isAlias(String name) {
        return false;
    }

    @Override
    public String[] getAliases(String name) {
        return new String[0];
    }
}
