/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nr.data.gather")
public class DataGatherConfig {
    private Boolean isAvg = false;
    private Integer avgSize = 250;
    private Boolean debug = false;
    private Integer lockWaitTime = 5000;

    public Integer getAvgSize() {
        return this.avgSize;
    }

    public void setAvgSize(Integer avgSize) {
        this.avgSize = avgSize;
    }

    public Boolean enableDebug() {
        return this.debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Boolean getAvg() {
        return this.isAvg;
    }

    public void setAvg(Boolean avg) {
        this.isAvg = avg;
    }

    public Integer getLockWaitTime() {
        return this.lockWaitTime;
    }

    public void setLockWaitTime(Integer lockWaitTime) {
        this.lockWaitTime = lockWaitTime;
    }
}

