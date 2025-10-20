/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class Logger
implements org.slf4j.Logger {
    private org.slf4j.Logger log = null;

    public Logger(Class<?> cls) {
        this.log = LoggerFactory.getLogger(cls);
    }

    @Override
    public String getName() {
        return this.log.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.log.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        this.log.trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        this.log.trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        this.log.trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object ... arguments) {
        this.log.trace(format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        this.log.trace(msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return this.log.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        this.log.trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        this.log.trace(marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        this.log.trace(marker, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object ... argArray) {
        this.log.trace(marker, format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        this.log.trace(marker, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.log.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        this.log.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        this.log.debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        this.log.debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object ... arguments) {
        this.log.debug(format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        this.log.debug(msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return this.log.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {
        this.log.debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        this.log.debug(marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        this.log.debug(marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object ... arguments) {
        this.log.debug(marker, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        this.log.debug(marker, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.log.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        this.log.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        this.log.info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        this.log.info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object ... arguments) {
        this.log.info(format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        this.log.info(msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return this.log.isDebugEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {
        this.log.info(marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        this.log.info(marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        this.log.info(marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object ... arguments) {
        this.log.info(marker, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        this.log.info(marker, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.log.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        this.log.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        this.log.warn(format, arg);
    }

    @Override
    public void warn(String format, Object ... arguments) {
        this.log.warn(format, arguments);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        this.log.warn(format, arg1, arg2);
    }

    @Override
    public void warn(String msg, Throwable t) {
        this.log.warn(msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return this.log.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {
        this.log.warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        this.log.warn(marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        this.log.warn(marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object ... arguments) {
        this.log.warn(marker, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        this.log.warn(marker, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.log.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        this.log.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        this.log.error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        this.log.error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object ... arguments) {
        this.log.error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        this.log.error(msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return this.log.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {
        this.log.error(marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        this.log.error(marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        this.log.error(marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object ... arguments) {
        this.log.error(marker, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        this.log.error(marker, msg, t);
    }

    public void addLog(String msg, int level, String userInfo) {
        this.info("\u8bb0\u5165\u6570\u636e\u5e93\u65e5\u5fd7\u8868\uff1a" + msg);
    }
}

