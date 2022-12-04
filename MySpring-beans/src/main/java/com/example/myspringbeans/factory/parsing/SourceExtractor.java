package com.example.myspringbeans.factory.parsing;

import com.myspringcore.core.io.Resource;
import org.springframework.lang.Nullable;

/**
 * 简单的策略，允许工具控制源元数据的附加方式到bean定义元数据。
 *
 * @author julu
 * @date 2022/12/4 13:28
 */
@FunctionalInterface
public interface SourceExtractor {

    @Nullable
    Object extractSource(Object sourceCandidate, @Nullable Resource definingResource);
}
