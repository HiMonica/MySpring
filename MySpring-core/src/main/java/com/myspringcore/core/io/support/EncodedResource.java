package com.myspringcore.core.io.support;

import com.myspringcore.core.io.InputStreamSource;
import com.myspringcore.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author julu
 * @date 2022/11/27 09:47
 */
public class EncodedResource implements InputStreamSource {

    private final Resource resource;

    @Nullable
    private final String encoding;

    @Nullable
    private final Charset charset;

    public EncodedResource(Resource resource){
        this(resource, null ,null);
    }

    public EncodedResource(Resource resource, @Nullable String encoding){
        this(resource, encoding, null);
    }

    public EncodedResource(Resource resource, @Nullable Charset charset){
        this(resource, null, charset);
    }

    public EncodedResource(Resource resource, @Nullable String encoding, @Nullable Charset charset){
        super();
        Assert.notNull(resource, "Resource must not be null");
        this.resource = resource;
        this.encoding = encoding;
        this.charset = charset;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    /**
     * 返回持有的资源
     */
    public final Resource getResource(){
        return this.resource;
    }

    /**
     * 返回该资源的编码格式
     */
    @Nullable
    public String getEncoding() {
        return this.encoding;
    }
}
