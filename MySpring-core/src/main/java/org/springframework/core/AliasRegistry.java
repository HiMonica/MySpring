package org.springframework.core;

/**
 * 管理别名的通用接口，作为超级接口
 *
 * @author julu
 * @date 2022/9/19 23:51
 */
public interface AliasRegistry {

    /**
     * 给一个名字，注册一个别名
     *
     * @param name
     * @param alias
     */
    void registerAlias(String name, String alias);

    /**
     * 从注册表中删除指定的别名
     *
     * @param alias
     */
    void removeAlias(String alias);

    /**
     * 该名字是否存在别名
     *
     * @param name
     * @return
     */
    boolean isAlias(String name);

    /**
     * 通过名字返回一个别名
     *
     * @param name
     * @return
     */
    String[] getAliases(String name);
}
