package com.example.myspringbeans.xml;

import org.springframework.beans.factory.xml.BeansDtdResolver;
import org.springframework.beans.factory.xml.PluggableSchemaResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author julu
 * @date 2022/11/20 19:45
 */
public class DelegatingEntityResolver implements EntityResolver {

    /** Suffix for DTD files. */
    public static final String DTD_SUFFIX = ".dtd";

    /** Suffix for schema definition files. */
    public static final String XSD_SUFFIX = ".xsd";

    private final EntityResolver dtdResolver;

    private final EntityResolver schemaResolver;

    /**
     * 创建一个新的DelegatingEntityResolver来委派
     */
    public DelegatingEntityResolver(@Nullable ClassLoader classLoader){
        this.dtdResolver = new BeansDtdResolver();
        this.schemaResolver = new PluggableSchemaResolver(classLoader);
    }

    /**
     * 创建一个新的DelegatingEntityResolver来委派
     */
    public DelegatingEntityResolver(EntityResolver dtdResolver, EntityResolver schemaResolver){
        Assert.notNull(dtdResolver, "'dtdResolver' is required");
        Assert.notNull(schemaResolver, "'schemaResolver' is required");
        this.dtdResolver = dtdResolver;
        this.schemaResolver = schemaResolver;
    }

    @Override
    public InputSource resolveEntity(@Nullable String publicId, @Nullable String systemId)
            throws SAXException, IOException {

        if (systemId != null){
            if (systemId.endsWith(DTD_SUFFIX)){
                return this.dtdResolver.resolveEntity(publicId, systemId);
            }
            else if (systemId.endsWith(XSD_SUFFIX)){
                return this.schemaResolver.resolveEntity(publicId, systemId);
            }
        }

        return null;
    }
}
