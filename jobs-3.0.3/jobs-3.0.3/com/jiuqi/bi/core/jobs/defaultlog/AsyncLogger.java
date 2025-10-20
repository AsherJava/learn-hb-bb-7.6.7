/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.defaultlog.LogItem;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.jobs.defaultlog.LoggerConsumer;

public class AsyncLogger
extends Logger {
    public AsyncLogger(JobContext context) {
        super(context);
    }

    @Override
    void doLog(LogItem item) {
        LoggerConsumer.getInstance().addItem(item);
    }
}

