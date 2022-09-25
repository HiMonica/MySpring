package org.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * @author julu
 * @date 2022/9/11 14:45
 */
public interface Resource extends InputStreamSource{

    /**
     * 是否存在该资源
     * @return
     */
    boolean exists();

    /**
     * 是否可以读取此资源的非空内容
     * @return
     */
    default boolean isReadable(){
        return exists();
    }

    /**
     * 是否具有打开此资源的句柄
     * @return
     */
    default boolean isOpen(){
        return false;
    }

    /**
     * 此资源是否是文件
     * @return
     */
    default boolean isFile(){
        return false;
    }

    /**
     * 获取资源URL
     * @return
     * @throws IOException
     */
    URL getURL() throws IOException;

    /**
     * 获取资源URI
     * @return
     * @throws IOException
     */
    URI getURI() throws IOException;

    /**
     * 获取资源文件
     * @return
     * @throws IOException
     */
    File getFile() throws IOException;
}
