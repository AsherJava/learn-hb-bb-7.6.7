/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.DummyLogger
 *  com.jiuqi.bi.logging.ILogger
 */
package com.jiuqi.bi.quickreport.log;

import com.jiuqi.bi.logging.DummyLogger;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.quickreport.log.ILoggerProvider;

public class LoggerHelper {
    private static LoggerHelper instance = new LoggerHelper();
    private ILoggerProvider loggerProvider = new DummyLoggerProvider();

    private LoggerHelper() {
    }

    public static LoggerHelper getInstance() {
        return instance;
    }

    public void setLogger(ILoggerProvider loggerProvider) {
        this.loggerProvider = loggerProvider;
    }

    public void debug(Throwable t) {
        this.loggerProvider.getLogger().debug(t.getMessage(), t);
    }

    public void info(Throwable t) {
        this.loggerProvider.getLogger().info(t.getMessage(), t);
    }

    public void warn(Throwable t) {
        this.loggerProvider.getLogger().warn(t.getMessage(), t);
    }

    public void error(Throwable t) {
        this.loggerProvider.getLogger().error(t.getMessage(), t);
    }

    class DummyLoggerProvider
    implements ILoggerProvider {
        DummyLoggerProvider() {
        }

        @Override
        public ILogger getLogger() {
            return new DummyLogger();
        }
    }
}

