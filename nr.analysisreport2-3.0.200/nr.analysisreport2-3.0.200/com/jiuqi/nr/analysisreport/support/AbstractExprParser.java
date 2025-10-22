/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.support;

import com.jiuqi.nr.analysisreport.support.IExprParser;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public abstract class AbstractExprParser
implements IExprParser {
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return this.threadPoolTaskExecutor;
    }

    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }
}

