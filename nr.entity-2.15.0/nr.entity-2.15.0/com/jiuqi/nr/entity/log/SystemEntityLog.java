/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.log;

import com.jiuqi.nr.entity.log.EntityLogger;
import com.jiuqi.nr.entity.log.LoggerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemEntityLog
implements EntityLogger {
    private static final Logger log = LoggerFactory.getLogger("com.jiuqi.nr.entity.logger");
    private final String traceId;

    public SystemEntityLog(String traceId) {
        this.traceId = traceId;
        log.debug("newEntityLogger:{}", (Object)traceId);
    }

    @Override
    public void info(String msg) {
        log.info(this.buildFormat(msg));
    }

    @Override
    public void info(String format, Object ... arguments) {
        log.info(this.buildFormat(format), arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        log.info(this.buildFormat(msg), t);
    }

    @Override
    public void debug(String msg) {
        if (log.isDebugEnabled()) {
            log.debug(this.buildFormat(msg));
        }
    }

    @Override
    public void debug(String format, Object ... arguments) {
        if (log.isDebugEnabled()) {
            log.debug(this.buildFormat(format), arguments);
        }
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (log.isDebugEnabled()) {
            log.debug(this.buildFormat(msg), t);
        }
    }

    @Override
    public void trace(String msg) {
        if (log.isTraceEnabled()) {
            log.trace(this.buildFormat(msg));
        }
    }

    @Override
    public void trace(String format, Object ... arguments) {
        if (log.isTraceEnabled()) {
            log.trace(this.buildFormat(format), arguments);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (log.isTraceEnabled()) {
            log.trace(this.buildFormat(msg), t);
        }
    }

    @Override
    public void error(String msg) {
        log.error(this.buildFormat(msg));
    }

    @Override
    public void error(String format, Object ... arguments) {
        log.error(this.buildFormat(format), arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        log.error(this.buildFormat(msg), t);
    }

    @Override
    public void accept(LoggerAction loggerAction) {
        loggerAction.accept(this);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    private String buildFormat(String format) {
        return "[" + this.traceId + "]" + format;
    }
}

