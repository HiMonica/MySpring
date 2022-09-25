package org.springframework.core.io;

import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

/**
 * @author julu
 * @date 2022/9/12 16:26
 */
public class DefaultResourceLoader implements ResourceLoader{

    @Nullable
    private ClassLoader classLoader;

    @Override
    public Resource getResource(String location) {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return (this.classLoader != null) ? this.classLoader : ClassUtils.getDefaultClassLoader();
    }
}
