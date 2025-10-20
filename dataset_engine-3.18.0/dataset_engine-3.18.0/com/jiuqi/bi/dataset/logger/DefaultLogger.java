/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.logger;

import com.jiuqi.bi.dataset.logger.ILogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogger
implements ILogger {
    private Logger logger = LoggerFactory.getLogger("com.jiuqi.bi.dataset");

    @Override
    public void debug(String message) {
        this.logger.debug(message);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }
}

