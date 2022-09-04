package org.apache.commons.logging;

/**
 * @author julu
 * @date 2022/9/4 15:47
 */
public abstract class LogFactory {

    public static Log getLog(Class<?> clazz){
        return getLog(clazz.getName());
    }

    public static Log getLog(String name){
        return LogAdapter.createLog(name);
    }

}
