/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import com.jiuqi.bi.logging.ILogger;

public class DummyLogger
implements ILogger {
    @Override
    public String getName() {
        return "com.jiuqi.bi.logger.dummy";
    }

    @Override
    public void trace(String message) {
    }

    @Override
    public void trace(String message, Throwable e) {
    }

    @Override
    public void debug(String message) {
    }

    @Override
    public void debug(String message, Throwable e) {
    }

    @Override
    public void info(String message) {
    }

    @Override
    public void info(String message, Throwable e) {
    }

    @Override
    public void warn(String message) {
    }

    @Override
    public void warn(String message, Throwable e) {
    }

    @Override
    public void error(String message) {
    }

    @Override
    public void error(String message, Throwable e) {
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }
}

