/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import org.slf4j.helpers.MessageFormatter;

public interface ILogger {
    public String getName();

    public void trace(String var1);

    public void trace(String var1, Throwable var2);

    default public void trace(String format, Object ... arguments) {
        if (this.isTraceEnabled()) {
            this.trace(MessageFormatter.arrayFormat(format, arguments).getMessage());
        }
    }

    public void debug(String var1);

    public void debug(String var1, Throwable var2);

    default public void debug(String format, Object ... arguments) {
        if (this.isDebugEnabled()) {
            this.debug(MessageFormatter.arrayFormat(format, arguments).getMessage());
        }
    }

    public void info(String var1);

    public void info(String var1, Throwable var2);

    default public void info(String format, Object ... arguments) {
        if (this.isInfoEnabled()) {
            this.info(MessageFormatter.arrayFormat(format, arguments).getMessage());
        }
    }

    public void warn(String var1);

    public void warn(String var1, Throwable var2);

    default public void warn(String format, Object ... arguments) {
        if (this.isWarnEnabled()) {
            this.warn(MessageFormatter.arrayFormat(format, arguments).getMessage());
        }
    }

    public void error(String var1);

    public void error(String var1, Throwable var2);

    default public void error(String format, Object ... arguments) {
        if (this.isErrorEnabled()) {
            this.warn(MessageFormatter.arrayFormat(format, arguments).getMessage());
        }
    }

    public boolean isTraceEnabled();

    public boolean isDebugEnabled();

    public boolean isInfoEnabled();

    public boolean isWarnEnabled();

    public boolean isErrorEnabled();
}

