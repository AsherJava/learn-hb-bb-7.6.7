/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StackTraceUtil {
    private static final Logger logger = LoggerFactory.getLogger(StackTraceUtil.class);

    public static void printStackTrace(String msg) {
        logger.debug("\u72b6\u6001\uff1a{}", (Object)msg);
        if (logger.isTraceEnabled()) {
            StackTraceElement[] stackTrace;
            for (StackTraceElement element : stackTrace = Thread.currentThread().getStackTrace()) {
                logger.trace("\u7c7b\u8def\u5f84\uff1a{} \u65b9\u6cd5\u540d\uff1a{} \u8c03\u7528\u884c\u53f7\uff1a{}", element.getClassName(), element.getMethodName(), element.getLineNumber());
            }
        }
    }
}

