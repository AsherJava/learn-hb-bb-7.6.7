/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.common.utils;

import com.jiuqi.nr.data.estimation.common.StringLogger;
import org.slf4j.Logger;

public class FormDataIOLogger
extends StringLogger {
    protected Logger logger;

    public FormDataIOLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void logInfo(String msg) {
        super.logInfo(msg);
        this.logger.info(msg);
    }

    @Override
    public void logWarn(String msg) {
        super.logWarn(msg);
        this.logger.warn(msg);
    }

    @Override
    public void logError(String msg) {
        super.logError(msg);
        this.logger.error(msg);
    }

    public void resetProcess() {
        this.process = 0.0;
    }
}

