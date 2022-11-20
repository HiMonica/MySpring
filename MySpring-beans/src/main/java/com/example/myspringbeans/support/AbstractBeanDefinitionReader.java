package com.example.myspringbeans.support;

import com.example.myspringbeans.xml.DelegatingEntityResolver;
import com.example.myspringbeans.xml.ResourceEntityResolver;
import com.myspringcore.core.env.Environment;
import com.myspringcore.core.env.EnvironmentCapable;
import com.myspringcore.core.env.StandardEnvironment;
import com.myspringcore.core.io.ResourceLoader;
import com.myspringcore.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.xml.sax.EntityResolver;

/**
 * @author julu
 * @date 2022/11/20 17:37
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader, EnvironmentCapable {

    private final BeanDefinitionRegistry registry;

    @Nullable
    private ResourceLoader resourceLoader;

    @Nullable
    private ClassLoader beanClassLoader;

    private Environment environment;

    @Nullable
    private EntityResolver entityResolver;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
        this.registry = registry;

        if (this.registry instanceof ResourceLoader){
            this.resourceLoader = (ResourceLoader) this.registry;
        }
        else {
            this.resourceLoader = new PathMatchingResourcePatternResolver();
        }

        //如果可能的话继承环境
        if (this.registry instanceof EnvironmentCapable){
            this.environment = ((EnvironmentCapable) this.registry).getEnvironment();
        }
        else {
            this.environment = new StandardEnvironment();
        }
    }

    /**
     * 设置读取bean定义时使用的环境
     */
    public void setEnvironment(Environment environment){
        Assert.notNull(environment, "Environment must not be null");
        this.environment = environment;
    }

    /**
     * 将ResourceLoader设置为用于资源位置
     */
    public void setResourceLoader(@Nullable ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    /**
     * 设置用于解析的SAX实体解析器
     */
    public void setEntityResolver(@Nullable EntityResolver entityResolver){
        this.entityResolver = entityResolver;
    }

    /**
     * 返回要使用的EntityResolver，构建一个默认的解析器
     */
    public EntityResolver setEntityResolver(){
        if (this.entityResolver == null){
            // 确定要使用的默认EntityResolver
            ResourceLoader resourceLoader = getResourceLoader();
            if (resourceLoader != null){
                this.entityResolver = new ResourceEntityResolver(resourceLoader);
            }
            else {
                this.entityResolver = new DelegatingEntityResolver(getBeanClassLoader());
            }
        }
        return this.entityResolver;
    }
}
