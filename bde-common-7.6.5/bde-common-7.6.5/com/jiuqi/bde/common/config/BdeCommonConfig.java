/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class BdeCommonConfig {
    private Long fetchTaskSleepTime = 1000L;
    private Long fetchSleepTime = 500L;

    public Long getFetchTaskSleepTime() {
        return this.fetchTaskSleepTime;
    }

    @Value(value="${jiuqi.bde.fetch.task.sleep-time:1000}")
    public void setFetchTaskSleepTime(Long fetchTaskSleepTime) {
        if (fetchTaskSleepTime != null && fetchTaskSleepTime >= 150L && fetchTaskSleepTime <= 5000L) {
            this.fetchTaskSleepTime = this.fetchSleepTime;
        }
    }

    public Long getFetchSleepTime() {
        return this.fetchSleepTime;
    }

    @Value(value="${jiuqi.bde.fetch.execute.sleep-time:500}")
    public void setFetchSleepTime(Long fetchSleepTime) {
        if (fetchSleepTime != null && fetchSleepTime >= 150L && fetchSleepTime <= 5000L) {
            this.fetchSleepTime = fetchSleepTime;
        }
    }
}

