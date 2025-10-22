/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger log = LoggerFactory.getLogger(Log.class);

    public static void error(Exception e) {
        log.error(e.getMessage(), e);
    }

    public static void info(String info) {
        log.info(info);
    }
}

