/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="bpm.threadpool", ignoreUnknownFields=true)
public class ThreadPoolProperties {
    protected static final String PROPERTIES_PREFIX = "bpm.threadpool";
    private int corePoolSize = 2;
    private int keepAliveSeconds = 200;
    private int maxPoolSize = 10;
    private int queueCapacity = 20;

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getKeepAliveSeconds() {
        return this.keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return this.queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }
}

