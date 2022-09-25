package com.myspringcore.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author julu
 * @date 2022/9/11 14:46
 */
public interface InputStreamSource {

    InputStream getInputStream() throws IOException;
}
