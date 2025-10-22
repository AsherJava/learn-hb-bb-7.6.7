/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.pool;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="custom.taskpool.efdc-data-check")
public class EFDCDataCheckThreadPoolProperties {
    private static EFDCDataCheckThreadPoolProperties instance;
    private int corePoolSize = 10;
    private int maxPoolSize = 10;
    private int keepAliveSeconds = 60;
    private int queueCapacity = 200;

    private EFDCDataCheckThreadPoolProperties() {
    }

    public static EFDCDataCheckThreadPoolProperties getInstance() {
        if (instance == null) {
            instance = new EFDCDataCheckThreadPoolProperties();
        }
        return instance;
    }

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

