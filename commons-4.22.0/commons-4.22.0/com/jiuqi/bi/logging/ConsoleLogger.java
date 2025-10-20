/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import com.jiuqi.bi.logging.ILogger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger
implements ILogger {
    private final String name;
    private final int level;
    public static final int TRACE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;

    public ConsoleLogger(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public ConsoleLogger(String name) {
        this(name, 1);
    }

    @Override
    public String getName() {
        return this.name;
    }

    private String formatMessage(String level, String message) {
        StringBuilder buffer = new StringBuilder();
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss(SSS)");
        buffer.append(dateFmt.format(new Date())).append(" [").append(level).append("] ");
        if (message != null) {
            buffer.append(message);
        }
        return buffer.toString();
    }

    @Override
    public void trace(String message) {
        if (this.isTraceEnabled()) {
            System.out.println(this.formatMessage("TRACE", message));
        }
    }

    @Override
    public void trace(String message, Throwable e) {
        if (this.isTraceEnabled()) {
            this.trace(message);
            e.printStackTrace();
        }
    }

    @Override
    public void debug(String message) {
        if (this.isDebugEnabled()) {
            System.out.println(this.formatMessage("DEBUG", message));
        }
    }

    @Override
    public void debug(String message, Throwable e) {
        if (this.isDebugEnabled()) {
            this.debug(message);
            e.printStackTrace();
        }
    }

    @Override
    public void info(String message) {
        if (this.isInfoEnabled()) {
            System.out.println(this.formatMessage("INFO", message));
        }
    }

    @Override
    public void info(String message, Throwable e) {
        if (this.isInfoEnabled()) {
            this.info(message);
            e.printStackTrace();
        }
    }

    @Override
    public void warn(String message) {
        if (this.isWarnEnabled()) {
            System.out.println(this.formatMessage("WARN", message));
        }
    }

    @Override
    public void warn(String message, Throwable e) {
        if (this.isWarnEnabled()) {
            this.warn(message);
            e.printStackTrace();
        }
    }

    @Override
    public void error(String message) {
        if (this.isErrorEnabled()) {
            System.out.println(this.formatMessage("ERROR", message));
        }
    }

    @Override
    public void error(String message, Throwable e) {
        if (this.isErrorEnabled()) {
            this.error(message);
            e.printStackTrace();
        }
    }

    @Override
    public boolean isTraceEnabled() {
        return this.level <= 1;
    }

    @Override
    public boolean isDebugEnabled() {
        return this.level <= 2;
    }

    @Override
    public boolean isInfoEnabled() {
        return this.level <= 3;
    }

    @Override
    public boolean isWarnEnabled() {
        return this.level <= 4;
    }

    @Override
    public boolean isErrorEnabled() {
        return this.level <= 5;
    }
}

