/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.JobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImmediatelyLogger
extends com.jiuqi.bi.core.jobs.defaultlog.Logger {
    private static final Logger logger = LoggerFactory.getLogger(ImmediatelyLogger.class);

    public ImmediatelyLogger(JobContext context) {
        super(context);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void debug(String message, Throwable e) {
        logger.debug(message, e);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(String message, Throwable e) {
        logger.error(message, e);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void info(String message, Throwable e) {
        logger.info(message, e);
    }

    @Override
    public void doLog(int level, String message, Throwable e) {
        logger.info(message, e);
    }

    @Override
    public void trace(String message) {
        logger.trace(message);
    }

    @Override
    public void trace(String message, Throwable e) {
        logger.trace(message, e);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void warn(String message, Throwable e) {
        logger.warn(message, e);
    }
}

