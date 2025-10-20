/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.logging.SLFLogger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogManager {
    private static Map<String, ILogger> loggers = new ConcurrentHashMap<String, ILogger>();

    private LogManager() {
    }

    public static ILogger getLogger(String name) {
        ILogger log = loggers.get(name);
        return log == null ? new SLFLogger(name) : log;
    }

    public static void setLogger(String name, ILogger logger) {
        loggers.put(name, logger);
    }

    public static ILogger getLogger(Class<?> klass) {
        return LogManager.getLogger(klass.getCanonicalName());
    }
}

