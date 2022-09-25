package org.springframework.core.env;

public interface EnvironmentCapable {

    /**
     * 返回与此组件关联的Environment
     */
    Environment getEnvironment();
}
