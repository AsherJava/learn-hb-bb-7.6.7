/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 */
package com.jiuqi.nr.data.estimation.sub.database.common;

import com.jiuqi.nr.data.estimation.common.StringLogger;
import org.slf4j.Logger;

public class SubDatabaseLogger
extends StringLogger {
    protected Logger logger;

    public SubDatabaseLogger(Logger logger) {
        this.logger = logger;
    }

    public void logInfo(String msg) {
        super.logInfo(msg);
        this.logger.info(msg);
    }

    public void logWarn(String msg) {
        super.logWarn(msg);
        this.logger.warn(msg);
    }

    public void logError(String msg) {
        super.logError(msg);
        this.logger.error(msg);
    }

    public void resetProcess() {
        this.process = 0.0;
    }
}

