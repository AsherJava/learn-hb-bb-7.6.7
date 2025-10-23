/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FMDMLogUtil {
    private static final Logger log = LoggerFactory.getLogger(FMDMLogUtil.class);

    public static void logInfo(String format, Object ... arguments) {
        log.info(format, arguments);
    }

    public static void logError(String format, Object ... arguments) {
        log.error(format, arguments);
    }

    public static void logError(String msg, Throwable t) {
        log.error(msg, t);
    }

    public static void logDebug(String format, Object ... arguments) {
        if (log.isDebugEnabled()) {
            log.debug(format, arguments);
        }
    }
}

