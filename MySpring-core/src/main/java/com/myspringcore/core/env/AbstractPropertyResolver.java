package com.myspringcore.core.env;

import com.myspringcore.util.PropertyPlaceholderHelper;
import org.springframework.lang.Nullable;
import org.springframework.util.SystemPropertyUtils;

/**
 * @author julu
 * @date 2022/11/27 15:56
 */
public abstract class AbstractPropertyResolver implements ConfigurablePropertyResolver{

    @Nullable
    private PropertyPlaceholderHelper nonStrictHelper;

    @Nullable
    private PropertyPlaceholderHelper strictHelper;

    private boolean ignoreUnresolvableNestedPlaceholders = false;

    private String placeholderPrefix = SystemPropertyUtils.PLACEHOLDER_PREFIX;

    private String placeholderSuffix = SystemPropertyUtils.PLACEHOLDER_SUFFIX;

    @Nullable
    private String valueSeparator = SystemPropertyUtils.VALUE_SEPARATOR;

    public String resolveRequirePlaceholders(String text) throws IllegalArgumentException {
        if (this.strictHelper == null){
            this.strictHelper = createPlaceholderHelper(false);
        }
        return doResolvePlaceholders(text, this.nonStrictHelper);
    }

    private String doResolvePlaceholders(String text, PropertyPlaceholderHelper helper){
        return helper.replacePlaceholders(text, this::getPropertyAsRawString);
    }

    private PropertyPlaceholderHelper createPlaceholderHelper(boolean ignoreUnresolvablePlaceholders){
        return new PropertyPlaceholderHelper(this.placeholderPrefix, this.placeholderSuffix,
                this.valueSeparator, ignoreUnresolvablePlaceholders);
    }

    /**
     * 检索指定的属性作为原始字符串
     * 即没有嵌套占位符的解析。
     *
     * @param key
     * @return
     */
    @Nullable
    protected abstract String getPropertyAsRawString(String key);

}
