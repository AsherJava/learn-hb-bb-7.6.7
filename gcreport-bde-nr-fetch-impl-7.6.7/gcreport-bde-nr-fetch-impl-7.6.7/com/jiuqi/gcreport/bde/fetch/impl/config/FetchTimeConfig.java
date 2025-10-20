/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FetchTimeConfig {
    private Long fetchSleepTime;
    private Long planTaskFetchSleepTime;

    @Value(value="${jiuqi.bde.fetch.sleep.time:5}")
    public void setFetchSleepTime(Long fetchSleepTime) {
        this.fetchSleepTime = 1000L * fetchSleepTime;
    }

    @Value(value="${jiuqi.bde.plan.task.fetch.sleep.time:10}")
    public void setPlanTaskFetchSleepTime(Long planTaskFetchSleepTime) {
        this.planTaskFetchSleepTime = 1000L * planTaskFetchSleepTime;
    }

    public Long getFetchSleepTime() {
        return this.fetchSleepTime;
    }

    public Long getPlanTaskFetchSleepTime() {
        return this.planTaskFetchSleepTime;
    }
}

