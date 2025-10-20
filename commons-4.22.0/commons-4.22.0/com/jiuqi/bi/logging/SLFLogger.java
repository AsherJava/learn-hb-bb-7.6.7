/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import com.jiuqi.bi.logging.ILogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLFLogger
implements ILogger {
    private final Logger logger;

    public SLFLogger(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }

    public SLFLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public String getName() {
        return this.logger.getName();
    }

    @Override
    public void trace(String message) {
        this.logger.trace(message);
    }

    @Override
    public void trace(String message, Throwable e) {
        this.logger.trace(message, e);
    }

    @Override
    public void trace(String format, Object ... arguments) {
        this.logger.trace(format, arguments);
    }

    @Override
    public void debug(String message) {
        this.logger.debug(message);
    }

    @Override
    public void debug(String message, Throwable e) {
        this.logger.debug(message, e);
    }

    @Override
    public void debug(String format, Object ... arguments) {
        this.logger.debug(format, arguments);
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void info(String message, Throwable e) {
        this.logger.info(message, e);
    }

    @Override
    public void info(String format, Object ... arguments) {
        this.logger.info(format, arguments);
    }

    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }

    @Override
    public void warn(String message, Throwable e) {
        this.logger.warn(message, e);
    }

    @Override
    public void warn(String format, Object ... arguments) {
        this.logger.warn(format, arguments);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }

    @Override
    public void error(String message, Throwable e) {
        this.logger.error(message, e);
    }

    @Override
    public void error(String format, Object ... arguments) {
        this.logger.error(format, arguments);
    }

    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

    public String toString() {
        return "SLFLogger:" + this.getName();
    }
}

