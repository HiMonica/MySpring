package com.myspringcore.util;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 实用程序类，用于处理具有占位符值的字符串。
 * 占位符取该表单
 *
 * @author julu
 * @date 2022/12/4 16:54
 */
public class PropertyPlaceholderHelper {

    private static final Map<String, String> wellKnownSimplePrefixes = new HashMap<>(4);

    static {
        wellKnownSimplePrefixes.put("}", "{");
        wellKnownSimplePrefixes.put("]", "[");
        wellKnownSimplePrefixes.put(")", "(");
    }

    private final String placeholderPrefix;

    private final String placeholderSuffix;

    private final String simplePrefix;

    @Nullable
    private final String valueSeparator;

    private final boolean ignoreUnresolvablePlaceholders;

    public PropertyPlaceholderHelper(String placeholderPrefix, String placeholderSuffix,
                                     @Nullable String valeSeparator, boolean ignoreUnresolvablePlaceholders){
        Assert.notNull(placeholderPrefix, "'placeholderPrefix' must not be null");
        Assert.notNull(placeholderSuffix, "'placeholderSuffix' must not be null");
        this.placeholderPrefix = placeholderPrefix;
        this.placeholderSuffix = placeholderSuffix;
        String simplePrefixForSuffix = wellKnownSimplePrefixes.get(this.placeholderSuffix);
        if (simplePrefixForSuffix != null && this.placeholderPrefix.endsWith(simplePrefixForSuffix)){
            this.simplePrefix = simplePrefixForSuffix;
        }
        else {
            this.simplePrefix = this.placeholderPrefix;
        }
        this.valueSeparator = valeSeparator;
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
    }

    /**
     * 用返回值替换格式{@code ${name}}的所有占位符
     * 从提供的{@link PlaceholderResolver}。
     *
     */
    public String replacePlaceholders(String value, PlaceholderResolver placeholderResolver){
        Assert.notNull(value, "'value' must not be null");
        return parseStringValue(value, placeholderResolver, null);
    }

    private String parseStringValue(
            String value, PlaceholderResolver placeholderResolver, @Nullable Set<String> visitedPlaceholders) {

        int startIndex = value.indexOf(this.placeholderPrefix);
        if (startIndex == -1){
            return value;
        }

        StringBuilder result = new StringBuilder(value);
        while (startIndex != -1){

        }
        return null;
    }

    @FunctionalInterface
    public interface PlaceholderResolver{

        @Nullable
        String resolvePlaceholder(String placeholderName);
    }
}
