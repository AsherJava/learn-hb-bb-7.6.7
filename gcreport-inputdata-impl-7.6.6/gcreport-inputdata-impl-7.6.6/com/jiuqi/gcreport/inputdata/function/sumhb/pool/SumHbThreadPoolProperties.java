/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.pool;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="custom.taskpool.sumhb")
public class SumHbThreadPoolProperties {
    private int corePoolSize = 10;
    private int maxPoolSize = 20;
    private int keepAliveSeconds = 60;
    private int queueCapacity = 150;

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return this.keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return this.queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }
}

