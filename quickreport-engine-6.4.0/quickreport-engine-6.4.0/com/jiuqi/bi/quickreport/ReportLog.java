/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.logging.LogManager
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.logging.LogManager;

public class ReportLog {
    public static final String LOG_NAME = "com.jiuqi.bi.quickreport.engine";

    private ReportLog() {
    }

    public static ILogger openLogger() {
        return LogManager.getLogger((String)LOG_NAME);
    }
}

